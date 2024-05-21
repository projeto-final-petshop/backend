package br.com.project.petconnect.app.pet.service;

import br.com.project.petconnect.app.owner.domain.entities.OwnerEntity;
import br.com.project.petconnect.app.owner.repository.OwnerRepository;
import br.com.project.petconnect.app.pet.domain.dto.PetRequest;
import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import br.com.project.petconnect.app.pet.mapping.PetMapper;
import br.com.project.petconnect.app.pet.repository.PetRepository;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.app.user.repository.UserRepository;
import br.com.project.petconnect.core.exceptions.owner.OwnerNotFoundException;
import br.com.project.petconnect.core.exceptions.pet.PetNotFoundException;
import br.com.project.petconnect.core.exceptions.user.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public PetResponse registerPet(PetRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(UsernameNotFoundException::new);
        PetEntity pet = PetMapper.petMapper().toPetEntity(request);
        pet.setUser(user);
        petRepository.save(pet);
        return PetMapper.petMapper().toPetResponse(pet);
    }

    @Transactional
    public PetResponse updatePet(PetRequest request, Long id) {

        PetEntity pet = petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);

        if (request.getName() != null) {
            pet.setName(request.getName());
        }

        if (request.getBreed() != null) {
            pet.setBreed(request.getBreed());
        }

        PetEntity savedPet = petRepository.save(pet);

        return PetMapper.petMapper().toPetResponse(savedPet);

    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets() {
        List<PetEntity> petEntityList = petRepository.findAll();
        return PetMapper.petMapper().toPetResponseList(petEntityList);
    }

    @Transactional(readOnly = true)
    public PetResponse getPetById(Long id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
        return PetMapper.petMapper().toPetResponse(pet);
    }

    @Transactional
    public void deletePet(Long id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
        petRepository.delete(pet);
    }

}
