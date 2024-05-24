package br.com.project.petconnect.pet.service;

import br.com.project.petconnect.exceptions.PetNotFoundException;
import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.entities.Pet;
import br.com.project.petconnect.pet.mapping.PetMapper;
import br.com.project.petconnect.pet.repository.PetRepository;
import br.com.project.petconnect.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetResponse cadastrar(PetRequest request) {
        Pet pet = PetMapper.petMapper().toEntity(request);
//        User user = userRepository.findById(pet.getId()).orElseThrow();
//        pet.setUser(user);
        Pet savedPet = petRepository.save(pet);
        return PetMapper.petMapper().toResponse(savedPet);
    }

//    public PetResponse cadastrar(Long userId, PetRequest request) {
//        Pet pet = PetMapper.petMapper().toEntity(request);
//        User user = userRepository.findById(userId).orElseThrow();
//        pet.setUser(user);
//        Pet savedPet = petRepository.save(pet);
//        return PetMapper.petMapper().toResponse(savedPet);
//    }

    public PetResponse atualizar(PetRequest request) {

        Pet pet = PetMapper.petMapper().toEntity(request);

        pet = petRepository.findById(pet.getId())
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado!"));

        if (request.getName() != null) {
            pet.setName(request.getName());
        }

        if (request.getAge() != 0) {
            pet.setAge(request.getAge());
        }

        if (request.getColor() != null) {
            pet.setColor(request.getColor());
        }

        if (request.getBreed() != null) {
            pet.setBreed(request.getBreed());
        }

        return PetMapper.petMapper().toResponse(pet);
    }

    public void deletar(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado!"));
        petRepository.delete(pet);
    }

    public List<PetResponse> listar() {
        List<Pet> petList = petRepository.findAll();
        return PetMapper.petMapper().toResponseList(petList);
    }

    public PetResponse buscarPetPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado!"));
        return PetMapper.petMapper().toResponse(pet);
    }

    public PetResponse buscarPetPorNome(String name) {
        Pet pet = petRepository.findByName(name)
                .orElseThrow(() -> new PetNotFoundException("Nome do pet não encontrado!"));
        return PetMapper.petMapper().toResponse(pet);
    }

    public List<PetResponse> buscarPetPorUser(Long userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        return PetMapper.petMapper().toResponseList(pets);
    }

}
