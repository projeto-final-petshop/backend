package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PetServiceException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PetServiceUtils {

    static MessageUtil messageUtil;
    static PetRepository petRepository;

    public static Pet getPetByIdAndUser(Long petId, User user) {
        try {
            return petRepository.findByIdAndUser(petId, user)
                    .orElseThrow(() -> new PetNotFoundException(messageUtil.getMessage("notFound.pet")));
        } catch (Exception e) {
            log.error("Falha ao obter Pet pelo ID e usuário: {}", e.getMessage());
            throw new PetServiceException("Falha ao obter Pet pelo ID e usuário.");
        }
    }

    public static Pet findPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + petId));
    }

    public static void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setAge(petRequest.getAge());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setAnimalType(petRequest.getAnimalType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

}
