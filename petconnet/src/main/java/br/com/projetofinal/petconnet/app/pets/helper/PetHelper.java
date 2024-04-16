package br.com.projetofinal.petconnet.app.pets.helper;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import br.com.projetofinal.petconnet.app.users.entity.Users;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetHelper {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Pets findById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    public Pets saveUserAndPet(Pets pet) {
        Long userId = pet.getUser().getId();
        Users user = userRepository.findById(userId).orElseThrow(UsernameNotFoundException::new);
        pet.setUser(user);
        return petRepository.save(pet);
    }

    public static void mapToRequest(PetRequest request, Pets pet) {
        pet.setName(request.getName());
        pet.setBreed(request.getBreed());
        pet.setColor(request.getColor());
        pet.setAnimalType(request.getAnimalType());
    }


    public Pets savedPet(Pets pet) {
        pet = petRepository.save(pet);
        return pet;
    }

}
