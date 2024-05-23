package br.com.project.petconnect.pet.service;

import br.com.project.petconnect.exceptions.PetNotFoundException;
import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.entities.Pet;
import br.com.project.petconnect.pet.mapping.PetMapper;
import br.com.project.petconnect.pet.repository.PetRepository;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PetResponse registerPet(PetRequest petRequest) {
        User user = userRepository.findById(petRequest.getUserId()).orElseThrow();

        Pet pet = PetMapper.petMapper().toEntity(petRequest);
        pet.setUser(user);

        petRepository.save(pet);
        return PetMapper.petMapper().toResponse(pet);
    }

    @Transactional(readOnly = true)
    public List<PetResponse> listPets() {
        List<Pet> petEntityList = petRepository.findAll();
        return PetMapper.petMapper().toResponseList(petEntityList);
    }

    @Transactional(readOnly = true)
    public PetResponse getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado"));
        return PetMapper.petMapper().toResponse(pet);
    }

    /**
     * Listar Pets Cadastrados para um Determinado Usuário
     */
    public List<PetResponse> listPetsByUser(Long userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        return pets.stream()
                .map(PetMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado"));
        petRepository.delete(pet);
    }

}
