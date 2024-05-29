package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PetServiceException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final JwtService jwtService;
    private final MessageUtil messageUtil;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public PetResponse createPet(PetRequest petRequest, String authorizationHeader) {
        try {
            User user = getUserFromAuthorizationHeader(authorizationHeader);
            Pet pet = PetMapper.petMapper().toEntity(petRequest);
            pet.setUser(user);
            Pet savedPet = petRepository.save(pet);
            log.info("Pet cadastrado com sucesso: {}", savedPet.getId());
            return PetMapper.petMapper().toResponse(savedPet);
        } catch (UserNotFoundException | PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao cadastrar Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao cadastrar Pet. Por favor, tente novamente mais tarde.");
        }
    }

    public List<PetResponse> listPets(String authorizationHeader) {
        try {
            User user = getUserFromAuthorizationHeader(authorizationHeader);
            List<Pet> pets = petRepository.findByUser(user);
            log.info("Lista de pets cadastrados: {}", pets);
            return PetMapper.petMapper().toResponseList(pets);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new PetServiceException("Falha ao listar Pets. Por favor, tente novamente mais tarde.");
        }
    }

    public PetResponse getPetDetails(Long petId, String authorizationHeader) {
        try {
            User user = getUserFromAuthorizationHeader(authorizationHeader);
            Pet existingPet = getPetByIdAndUser(petId, user);
            log.info("Detalhes do Pet: {}", existingPet);
            return PetMapper.petMapper().toResponse(existingPet);
        } catch (UserNotFoundException | PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao obter detalhes do Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao obter detalhes do Pet. Por favor, tente novamente mais tarde.");
        }
    }

    public PetResponse updatePet(Long petId, PetRequest petRequest, String authorizationHeader) {
        try {
            User user = getUserFromAuthorizationHeader(authorizationHeader);
            Pet existingPet = getPetByIdAndUser(petId, user);
            updatePetDetails(existingPet, petRequest);
            petRepository.save(existingPet);
            log.info("Pet com ID {} atualizado com sucesso!", existingPet.getId());
            return PetMapper.petMapper().toResponse(existingPet);
        } catch (UserNotFoundException | PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao atualizar do Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao atualizar do Pet. Por favor, tente novamente mais tarde.");
        }
    }

    public void deletePet(Long petId, String authorizationHeader) {
        try {
            User user = getUserFromAuthorizationHeader(authorizationHeader);
            Pet existingPet = getPetByIdAndUser(petId, user);
            log.info("Pet com ID {} excluído com sucesso", existingPet.getId());
            petRepository.delete(existingPet);
        } catch (UserNotFoundException | PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao excluir do Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao excluir do Pet. Por favor, tente novamente mais tarde.");
        }
    }

    private User getUserFromAuthorizationHeader(String authorizationHeader) {
        try {
            String userEmail = jwtService.extractEmail(extractToken(authorizationHeader));
            return userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao obter usuário do cabeçalho de autorização: {}", e.getMessage());
            throw new PetServiceException("Falha ao obter usuário do cabeçalho de autorização.");
        }
    }

    private Pet getPetByIdAndUser(Long petId, User user) {
        try {
            return petRepository.findByIdAndUser(petId, user)
                    .orElseThrow(() -> new PetNotFoundException(messageUtil.getMessage("petNotFound")));
        } catch (PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao obter Pet pelo ID e usuário: {}", e.getMessage());
            throw new PetServiceException("Falha ao obter Pet pelo ID e usuário.");
        }
    }

    private void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setAge(petRequest.getAge());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setAnimalType(petRequest.getAnimalType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

    private String extractToken(String authorizationHeader) {
        try {
            return authorizationHeader.substring(7); // Remove "Bearer " do token
        } catch (Exception e) {
            log.error("Falha ao extrair token: {}", e.getMessage());
            throw new PetServiceException("Falha ao extrair token.");
        }
    }

}
