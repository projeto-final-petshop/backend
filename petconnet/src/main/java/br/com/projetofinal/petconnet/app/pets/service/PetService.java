package br.com.projetofinal.petconnet.app.pets.service;

import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.UnableToDeletePetException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.UnableToRegisterPetException;
import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.mapper.PetMapper;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
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

    @Transactional
    public PetResponse createPet(PetRequest request) {
        try {
            Pets pet = PetMapper.petMapper().toEntity(request);
            pet = petRepository.save(pet);
            return PetMapper.petMapper().toPetResponse(pet);
        } catch (UnableToRegisterPetException ex) {
            log.error("Erro ao tentar salvar Pet.");
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {
        List<Pets> pets = petRepository.findAll();
        return PetMapper.petMapper().toPetListResponse(pets);
    }

    @Transactional(readOnly = true)
    public PetResponse getPetById(Long id) {
        Pets pet = findPetById(id);
        return PetMapper.petMapper().toPetResponse(pet);
    }

    @Transactional
    public PetResponse updatePetById(Long id, PetRequest request) {
        Pets pet = findPetById(id);
        updatePetData(request, pet);
        return PetMapper.petMapper().toPetResponse(pet);
    }

    public void deletePet(Long id) {
        Pets pet = findPetById(id);
        try {
            petRepository.delete(pet);
        } catch (UnableToDeletePetException ex) {
            log.error("Erro ao tentar excluir Pet.");
            throw ex;
        }
    }

    private Pets findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    private void updatePetData(PetRequest request, Pets pet) {
        pet.setName(request.getName());
        pet.setUpdatedAt(LocalDateTime.now());
    }


}