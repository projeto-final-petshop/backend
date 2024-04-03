package br.com.projetofinal.petconnet.app.pets.service;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import br.com.projetofinal.petconnet.app.pets.helper.PetHelper;
import br.com.projetofinal.petconnet.app.pets.mapper.PetMapper;
import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetListException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetRegistrationException;
import br.com.projetofinal.petconnet.core.exceptions.errors.pets.PetRemoveException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final PetHelper petHelper;
    private final PetRepository petRepository;

    public PetResponse registerPet(PetRequest request) throws PetRegistrationException {
        Pets pet = PetMapper.petMapper().petRequestToPet(request);
        pet = petHelper.savedPet(pet);
        return PetMapper.petMapper().petToPetResponse(pet);
    }

    public PetResponse searchPetById(Long id) throws PetNotFoundException {
        Pets pet = petHelper.findById(id);
        return PetMapper.petMapper().petToPetResponse(pet);
    }

    public List<PetResponse> listAllPets() throws PetListException {
        List<Pets> petList = petRepository.findAll();
        return PetMapper.petMapper().petListToPetResponseList(petList);
    }

    public void updatePetById(Long id, PetRequest request) throws PetNotFoundException {
        Pets pet = petHelper.findById(id);
        PetHelper.mapToRequest(request, pet);
        pet = petHelper.savedPet(pet);
        PetMapper.petMapper().petToPetResponse(pet);
    }

    public void removePetById(Long id) throws PetRemoveException {
        petRepository.deleteById(id);
    }

}
