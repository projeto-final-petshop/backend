package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.generic.DataModificationException;
import br.com.finalproject.petconnect.exceptions.appointment.PetNotFoundException;
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

    public Pet getPetByIdAndUser(Long id, User user) {
        try {
            return petRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> {
                        log.error("Pet não encontrado para o usuário {} com ID: {}", user.getId(), id);
                        return new PetNotFoundException("Pet não encontrado.");
                    });
        } catch (Exception e) {
            log.error("Falha ao obter Pet pelo ID e usuário: {}", e.getMessage());
            throw new DataModificationException("Falha ao obter Pet pelo ID e usuário.");
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

}
