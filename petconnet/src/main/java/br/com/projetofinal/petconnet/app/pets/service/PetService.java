package br.com.projetofinal.petconnet.app.pets.service;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.dto.request.PetUpdateRequest;
import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.helper.PetHelper;
import br.com.projetofinal.petconnet.app.pets.mapper.PetMapper;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetListException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetRemoveException;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetHelper petHelper;

    @Transactional
    public PetResponse createPet(PetRequest petRequest) {

        User user = userRepository.findById(petRequest.getUserId())
                .orElseThrow(UsernameNotFoundException::new);

        Pets pet = PetMapper.petMapper().petRequestToPet(petRequest);

        pet.setUser(user);
        pet = petRepository.save(pet);

        return PetMapper.petMapper().petToPetResponse(pet);

    }

    @Transactional(readOnly = true)
    public PetResponse searchPetById(Long id) throws PetNotFoundException {
        Pets pet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pet Service --- Pet com o ID {} não encontrado.", id);
                    return new PetNotFoundException();
                });
        return PetMapper.petMapper().petToPetResponse(pet);
    }

    @Transactional(readOnly = true)
    public List<PetResponse> listAllPets() throws PetListException {
        List<Pets> petList = petRepository.findAll();
        return PetMapper.petMapper().petListToPetResponseList(petList);
    }

    @Transactional
    public PetResponse updatePet(PetUpdateRequest request) throws PetNotFoundException {
        Pets existingPet = petRepository.findById(request.getId())
                .orElseThrow(() -> {
                    log.error("Pet Service --- Pet com o ID {} não encontrado.", request.getId());
                    return new PetNotFoundException();
                });

        Pets updatePet = Pets.builder()
                .id(existingPet.getId())
                .name(request.getName())
                .breed(request.getBreed())
                .color(request.getColor())
                .birthdate(request.getBirthdate())
                .animalType(request.getAnimalType())
                .user(existingPet.getUser())
                .createdAt(existingPet.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        petRepository.save(updatePet);

        return PetResponse.builder()
                .id(updatePet.getId())
                .name(updatePet.getName())
                .breed(updatePet.getBreed())
                .color(updatePet.getColor())
                .birthdate(updatePet.getBirthdate())
                .animalType(updatePet.getAnimalType())
                .userId(updatePet.getUser() != null ? updatePet.getUser().getId() : null)
                .createdAt(updatePet.getCreatedAt())
                .updatedAt(updatePet.getUpdatedAt())
                .build();

    }

    @Transactional
    public void removePetById(Long id) throws PetRemoveException {
        petRepository.deleteById(id);
    }

}
