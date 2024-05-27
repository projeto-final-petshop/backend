package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public PetResponse createPet(PetRequest petRequest) {
        Pet pet = PetMapper.petMapper().toEntity(petRequest);
        Pet savedPet = petRepository.save(pet);
        return PetMapper.petMapper().toResponse(savedPet);
    }

    public String updatePet(Long id, PetRequest petRequest) {

        Optional<Pet> optionalPet = petRepository.findById(id);

        if (optionalPet.isPresent()) {

            Pet existingPet = optionalPet.get();

            if (petRequest.getName() != null) {
                existingPet.setName(petRequest.getName());
            }

            if (petRequest.getAge() != 0) {
                existingPet.setAge(petRequest.getAge());
            }

            if (petRequest.getColor() != null) {
                existingPet.setColor(petRequest.getColor());
            }

            if (petRequest.getBreed() != null) {
                existingPet.setBreed(petRequest.getBreed());
            }

            if (petRequest.getAnimalType() != null) {
                existingPet.setAnimalType(petRequest.getAnimalType());
            }

            if (petRequest.getBirthdate() != null) {
                existingPet.setBirthdate(petRequest.getBirthdate());
            }

            petRepository.save(existingPet);
            return "Pet updated succssfully!";

        }
        throw new PetNotFoundException("Pet not found with id: " + id);
    }

    public PetResponse getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + id));
        return PetMapper.petMapper().toResponse(pet);
    }

    public List<PetResponse> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return PetMapper.petMapper().toResponseList(pets);
    }

    public String deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + id));
        petRepository.delete(pet);
        return "Pet deleted successfuly!";
    }

}
