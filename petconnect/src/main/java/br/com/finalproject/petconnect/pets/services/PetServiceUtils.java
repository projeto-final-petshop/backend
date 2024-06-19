package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PetServiceUtils {

    static PetRepository petRepository;

    public void updatePetDetails(Pet existingPet, PetRequest petRequest) {
        existingPet.setName(petRequest.getName());
        existingPet.setAge(petRequest.getAge());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setAnimalType(petRequest.getAnimalType());
        existingPet.setBirthdate(petRequest.getBirthdate());
    }

}
