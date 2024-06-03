package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.pet.InvalidPetDataException;
import br.com.finalproject.petconnect.exceptions.runtimes.pet.PetNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PetServiceUtils {

    static PetRepository petRepository;

    public Pet getPetByIdAndUser(Long petId, User user) {
        try {
            return petRepository.findByIdAndUser(petId, user)
                    .orElseThrow(() -> {
                        log.error("Pet não encontrado para o usuário {} com ID: {}", user.getUsername(), petId);
                        return new PetNotFoundException("Pet não encontrado.");
                    });
        } catch (Exception e) {
            log.error("Falha ao obter Pet pelo ID e usuário: {}", e.getMessage());
            throw new InvalidPetDataException("Falha ao obter Pet pelo ID e usuário.");
        }
    }

    public void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setAge(petRequest.getAge());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setAnimalType(petRequest.getAnimalType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

    public static Pet findPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> {
                    log.error("Pet não encontrado com ID: {}", petId);
                    return new PetNotFoundException("Pet não encontrado com ID: " + petId);
                });
    }

}
