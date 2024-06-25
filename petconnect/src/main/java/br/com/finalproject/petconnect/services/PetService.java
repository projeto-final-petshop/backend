package br.com.finalproject.petconnect.services;

import br.com.finalproject.petconnect.domain.entities.Pet;

import java.util.List;

public interface PetServiceInt {

    List<Pet> findAll();

    Pet findById(Long id);

    Pet save(Pet pet);

    void deleteById(Long id);

}
