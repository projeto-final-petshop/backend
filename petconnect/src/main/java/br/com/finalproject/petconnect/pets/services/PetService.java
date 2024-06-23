package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.UserInactiveException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.EmailAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.security.PetPermissionDeniedException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.pets.dto.request.PetRequest;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final AuthUtils authUtils;
    private final PetRepository petRepository;

    @Transactional
    public PetResponse createPet(PetRequest petRequest, String authorizationHeader) {

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        userInactive(user);

        if (petRepository.existsByNameAndUser(petRequest.getName(), user)) {
            throw new EmailAlreadyExistsException();
        }

        Pet pet = PetMapper.petMapper().toEntity(petRequest);
        pet.setUser(user);

        try {
            Pet savedPet = petRepository.save(pet);
            log.info("Pet cadastrado com sucesso: {}", savedPet.getId());
            return PetMapper.petMapper().toResponse(savedPet);
        } catch (ServiceException e) {
            log.error("Falha ao cadastrar Pet: {}", e.getMessage());
            throw new ServiceException("Erro ao cadastrar pet.");
        }

    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets(String authorizationHeader) {

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        userInactive(user);

        try {
            List<Pet> pets = petRepository.findByUser(user);
            if (pets.isEmpty()) {
                throw new ResourceNotFoundException("Você não tem nenhum pet cadastrado.");
            }
            log.info("Lista de pets cadastrados para o usuário {}: {}", user.getUsername(), pets);
            return PetMapper.petMapper().toResponseList(pets);
        } catch (ServiceException e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new ServiceException("Erro ao listar pets.");
        }

    }

    @Transactional(readOnly = true)
    public PetResponse getPetDetails(Long id, String authorizationHeader) {

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        final var pet = petNotFound(id);

        if (!pet.getUser().equals(user)) {
            throw new PetPermissionDeniedException("visualizar");
        }

        userInactive(user);

        return PetMapper.INSTANCE.toResponse(pet);

    }

    @Transactional
    public PetResponse updatePet(Long id, PetRequest petRequest, String authorizationHeader) {

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        final var existingPet = petNotFound(id);

        if (!existingPet.getUser().equals(user)) {
            throw new PetPermissionDeniedException("atualizar");
        }

        userInactive(user);

        updatePetDetails(existingPet, petRequest);

        try {
            petRepository.save(existingPet);
            log.info("Pet com ID {} atualizado com sucesso!", existingPet.getId());
            return PetMapper.INSTANCE.toResponse(existingPet);
        } catch (ServiceException e) {
            log.error("Falha ao atualizar o Pet: {}", e.getMessage());
            throw new ServiceException("Erro ao atualizar pet.");
        }

    }

    @Transactional
    public void deletePet(Long id, String authorizationHeader) {

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        final var pet = petNotFound(id);

        if (!pet.getUser().equals(user)) {
            throw new PetPermissionDeniedException("excluir");
        }

        userInactive(user);

        petRepository.delete(pet);
        log.info("Pet com ID {} excluído com sucesso!", id);

    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {

        try {
            List<Pet> pets = petRepository.findAll();
            if (pets.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum pet cadastrado.");
            }
            return PetMapper.petMapper().toResponseList(pets);
        } catch (ServiceException e) {
            log.error("Falha ao listar todos os Pets: {}", e.getMessage());
            throw new ServiceException("Erro ao listar pets.");
        }

    }

    private Pet petNotFound(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pet não encontrado para o ID: {}", id);
                    return new ResourceNotFoundException("Pet não encontrado.");
                });
    }

    private void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setPetType(petRequest.getPetType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

    private static void userInactive(User user) {
        if (Boolean.FALSE.equals(user.getActive())) {
            throw new UserInactiveException();
        }
    }

}
