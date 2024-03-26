package br.com.projetofinal.petconnet.pets.service;

import br.com.projetofinal.petconnet.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.exceptions.errors.pets.UnableToRegisterPetException;
import br.com.projetofinal.petconnet.pets.dto.PetRequest;
import br.com.projetofinal.petconnet.pets.dto.PetResponse;
import br.com.projetofinal.petconnet.pets.entity.Pets;
import br.com.projetofinal.petconnet.pets.mapper.PetMapper;
import br.com.projetofinal.petconnet.pets.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Pets findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

}
