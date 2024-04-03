package br.com.projetofinal.petconnet.app.pets.helper;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetHelper {

    private final PetRepository petRepository;

    public static void mapToRequest(PetRequest request, Pets pet) {
        pet.setName(request.getName());
        pet.setAge(request.getAge());
        pet.setBreed(request.getBreed());
        pet.setColor(request.getColor());
        pet.setSpecies(request.getSpecies());
    }

    public Pets findById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    public Pets savedPet(Pets pet) {
        pet = petRepository.save(pet);
        return pet;
    }

}
