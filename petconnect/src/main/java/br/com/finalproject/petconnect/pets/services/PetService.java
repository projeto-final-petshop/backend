package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentListResponse;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.UserInactiveException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.PetAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.PetPermissionDeniedException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.pets.dto.request.PetRequest;
import br.com.finalproject.petconnect.pets.dto.response.PetListResponse;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final AuthUtils authUtils;
    private final PetRepository petRepository;

    @Transactional
    public PetResponse createPet(PetRequest petRequest, String authorizationHeader) {
        try {
            User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
            userInactive(user);

            if (petRepository.existsByNameAndUser(petRequest.getName(), user)) {
                log.warn("Tentativa de cadastrar pet com nome já existente para o usuário: {}", user.getUsername());
                throw new PetAlreadyExistsException("Pet com este nome já cadastrado para o usuário.");
            }

            Pet pet = PetMapper.petMapper().toEntity(petRequest);
            pet.setUser(user);

            Pet savedPet = petRepository.save(pet);
            log.info("Pet cadastrado com sucesso: {}", savedPet.getId());
            return PetMapper.petMapper().toResponse(savedPet);
        } catch (PetAlreadyExistsException e) {
            handlePetAlreadyExistsException(e);
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (UserInactiveException e) {
            handleUserInactiveException(e);
        } catch (Exception e) {
            log.error("Erro ao cadastrar pet", e);
            handleServiceException(e);
        }
        return null; // ou lançar outra exceção conforme a lógica da aplicação
    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets(String authorizationHeader) {
        try {
            User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
            userInactive(user);

            List<Pet> pets = petRepository.findByUser(user);
            log.info("Lista de pets cadastrados para o usuário {}: {}", user.getUsername(), pets);
            return PetMapper.petMapper().toResponseList(pets);
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (UserInactiveException e) {
            handleUserInactiveException(e);
        } catch (Exception e) {
            log.error("Erro ao listar pets", e);
            handleServiceException(e);
        }
        return Collections.emptyList(); // ou lançar outra exceção conforme a lógica da aplicação
    }

    @Transactional(readOnly = true)
    public PetListResponse getPetDetails(Long id, String authorizationHeader) {
        try {

            User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
            Pet pet = petRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado para o ID: " + id));

            if (!pet.getUser().getId().equals(user.getId())) {
                throw new PetPermissionDeniedException("Permissão negada para visualizar este pet.");
            }

            PetListResponse petResponse = PetMapper.INSTANCE.toPetListResponse(pet);
            List<AppointmentListResponse> appointmentResponses = pet.getAppointments().stream()
                    .map(appointment -> {
                        AppointmentListResponse appointmentResponse = new AppointmentListResponse();
                        appointmentResponse.setId(appointment.getId());
                        appointmentResponse.setServiceType(appointment.getServiceType());
                        appointmentResponse.setAppointmentDate(appointment.getAppointmentDate());
                        appointmentResponse.setAppointmentTime(appointment.getAppointmentTime());
                        appointmentResponse.setStatus(appointment.getStatus());
                        return appointmentResponse;
                    }).toList();
            petResponse.setAppointments(appointmentResponses);
            return petResponse;
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (PetPermissionDeniedException e) {
            handlePetPermissionDeniedException(e);
        } catch (Exception e) {
            log.error("Erro ao obter detalhes do pet", e);
            handleServiceException(e);
        }
        return null; // ou lançar outra exceção conforme a lógica da aplicação
    }

    @Transactional
    public PetResponse updatePet(Long id, PetRequest petRequest, String authorizationHeader) {
        try {
            User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

            Pet existingPet = petRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado para o ID: " + id));

            if (!existingPet.getUser().getId().equals(user.getId())) {
                log.warn("Usuário não autorizado tentou atualizar o pet com ID: {}", id);
                throw new PetPermissionDeniedException("Permissão negada para atualizar este pet.");
            }
            updatePetDetails(existingPet, petRequest);
            Pet savedPet = petRepository.save(existingPet);
            log.info("Pet com ID {} atualizado com sucesso!", savedPet.getId());
            return PetMapper.INSTANCE.toResponse(savedPet);
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (PetPermissionDeniedException e) {
            handlePetPermissionDeniedException(e);
        } catch (Exception e) {
            log.error("Erro ao atualizar pet", e);
            handleServiceException(e);
        }
        return null; // ou lançar outra exceção conforme a lógica da aplicação
    }

    @Transactional
    public void deletePet(Long id, String authorizationHeader) {
        try {
            User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
            Pet pet = petRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado para o ID: " + id));

            if (!pet.getUser().getId().equals(user.getId())) {
                log.warn("Usuário não autorizado tentou excluir o pet com ID: {}", id);
                throw new PetPermissionDeniedException("Permissão negada para excluir este pet.");
            }

            petRepository.delete(pet);
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (PetPermissionDeniedException e) {
            handlePetPermissionDeniedException(e);
        } catch (UserInactiveException e) {
            handleUserInactiveException(e);
        } catch (Exception e) {
            log.error("Erro ao excluir pet", e);
            handleServiceException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {
        try {
            List<Pet> pets = petRepository.findAll();
            if (pets.isEmpty()) {
                log.warn("Nenhum pet cadastrado.");
                throw new ResourceNotFoundException("Nenhum pet cadastrado.");
            }
            return PetMapper.petMapper().toResponseList(pets);
        } catch (ResourceNotFoundException e) {
            handleResourceNotFoundException(e);
        } catch (Exception e) {
            log.error("Erro ao listar todos os pets", e);
            handleServiceException(e);
        }
        return Collections.emptyList();
    }

    private void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setPetType(petRequest.getPetType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

    private void userInactive(User user) {
        if (Boolean.FALSE.equals(user.getActive())) {
            throw new UserInactiveException();
        }
    }

    private void handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("Recurso não encontrado: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    private void handlePetAlreadyExistsException(PetAlreadyExistsException e) {
        log.error("Pet já existe: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }

    private void handlePetPermissionDeniedException(PetPermissionDeniedException e) {
        log.error("Permissão negada para o pet: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
    }

    private void handleUserInactiveException(UserInactiveException e) {
        log.error("Usuário inativo: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    private void handleServiceException(Exception e) {
        if (e instanceof ServiceException) {
            log.error("Erro interno de serviço: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno de serviço", e);
        } else if (e instanceof DataAccessException) {
            log.error("Erro de acesso a dados: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso a dados", e);
        } else {
            log.error("Erro desconhecido: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro desconhecido", e);
        }
    }

}
