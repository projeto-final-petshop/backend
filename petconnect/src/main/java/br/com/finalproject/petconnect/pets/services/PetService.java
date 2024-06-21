package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.appointment.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.generic.DataModificationException;
import br.com.finalproject.petconnect.pets.dto.request.PetRequest;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
import br.com.finalproject.petconnect.utils.constants.ConstantsUtil;
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
            throw new DataModificationException(ConstantsUtil.FAILED_TO_REGISTER_PET);
        }
    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets(String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        try {
            List<Pet> pets = petRepository.findByUser(user);
            log.info("Lista de pets cadastrados para o usuário {}: {}", user.getUsername(), pets);
            return PetMapper.petMapper().toResponseList(pets);
        } catch (Exception e) {
            log.error("Falha ao listar Pets: {}", e.getMessage());
            throw new DataModificationException(ConstantsUtil.FAILED_TO_LIST_PET);
        }
    }

    @Transactional(readOnly = true)
    public PetResponse getPetDetails(Long id, String authorizationHeader) {
        authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ConstantsUtil.PET_NOT_FOUND_FOR_ID, id);
                    return new PetNotFoundException(ConstantsUtil.PET_NOT_FOUND);
                });
        return PetMapper.INSTANCE.toResponse(pet);
    }

    @Transactional
    public PetResponse updatePet(Long id, PetRequest petRequest, String authorizationHeader) {

        authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ConstantsUtil.PET_NOT_FOUND_FOR_ID, id);
                    return new PetNotFoundException(ConstantsUtil.PET_NOT_FOUND);
                });
        updatePetDetails(existingPet, petRequest);
        try {
            petRepository.save(existingPet);
            log.info("Pet com ID {} atualizado com sucesso!", existingPet.getId());
            return PetMapper.INSTANCE.toResponse(existingPet);
        } catch (Exception e) {
            log.error("Falha ao atualizar do Pet: {}", e.getMessage());
            throw new DataModificationException(ConstantsUtil.FAILED_TO_UPDATE_PET);
        }
    }

    @Transactional
    public void deletePet(Long id, String authorizationHeader) {
        authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(ConstantsUtil.PET_NOT_FOUND_FOR_ID, id);
                    return new PetNotFoundException(ConstantsUtil.PET_NOT_FOUND);
                });
        petRepository.delete(pet);
        log.info("Pet com ID {} excluído com sucesso!", id);
    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {
        try {
            List<Pet> pets = petRepository.findAll();
            return PetMapper.petMapper().toResponseList(pets);
        } catch (Exception e) {
            log.error("Falha ao listar todos os Pets: {}", e.getMessage());
            throw new DataModificationException(ConstantsUtil.FAILED_TO_LIST_ALL_PET);
        }
    }

    private void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setPetType(petRequest.getPetType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

}
