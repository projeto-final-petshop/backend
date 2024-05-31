package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PetServiceException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
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
        Pet pet = PetMapper.petMapper().toEntity(petRequest);
        pet.setUser(user);

        try {
            Pet savedPet = petRepository.save(pet);
            log.info("Pet cadastrado com sucesso: {}", savedPet.getId());
            return PetMapper.petMapper().toResponse(savedPet);
        } catch (Exception e) {
            log.error("Falha ao cadastrar Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao cadastrar Pet. Por favor, tente novamente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets(String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        try {
            List<Pet> pets = petRepository.findByUser(user);
            log.info("Lista de pets cadastrados: {}", pets);
            return PetMapper.petMapper().toResponseList(pets);
        } catch (Exception e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new PetServiceException("Falha ao listar Pets. Por favor, tente novamente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public PetResponse getPetDetails(Long petId, String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet existingPet = PetServiceUtils.getPetByIdAndUser(petId, user);
        log.info("Detalhes do Pet: {}", existingPet);
        return PetMapper.petMapper().toResponse(existingPet);
    }

    @Transactional
    public PetResponse updatePet(Long petId, PetRequest petRequest, String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet existingPet = PetServiceUtils.getPetByIdAndUser(petId, user);
        PetServiceUtils.updatePetDetails(existingPet, petRequest);
        try {
            petRepository.save(existingPet);
            log.info("Pet com ID {} atualizado com sucesso!", existingPet.getId());
            return PetMapper.petMapper().toResponse(existingPet);
        } catch (Exception e) {
            log.error("Falha ao atualizar do Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao atualizar do Pet. Por favor, tente novamente mais tarde.");
        }
    }

    @Transactional
    public void deletePet(Long petId, String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet existingPet = PetServiceUtils.getPetByIdAndUser(petId, user);
        try {
            petRepository.delete(existingPet);
            log.info("Pet com ID {} excluído com sucesso", existingPet.getId());
        } catch (Exception e) {
            log.error("Falha ao excluir do Pet: {}", e.getMessage());
            throw new PetServiceException("Falha ao excluir do Pet. Por favor, tente novamente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {
        try {
            List<Pet> pets = petRepository.findAll();
            return PetMapper.petMapper().toResponseList(pets);
        } catch (Exception e) {
            log.error("Falha ao listar todos os Pets: {}", e.getMessage());
            throw new PetServiceException("Falha ao listar todos os Pets. Por favor, tente novamente mais tarde.");
        }
    }

    @Transactional(readOnly = true)
    public PetResponse getPetById(Long petId) throws PetNotFoundException {
        Pet pet = PetServiceUtils.findPetById(petId);
        return PetMapper.petMapper().toResponse(pet);
    }

}
