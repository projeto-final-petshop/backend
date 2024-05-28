package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final PetMapper petMapper;
    private final MessageUtil messageUtil;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public PetResponse createPet(PetRequest petRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Pet pet = PetMapper.petMapper().toEntity(petRequest);
        pet.setUser(user);
        Pet savedPet = petRepository.save(pet);
        return PetMapper.petMapper().toResponse(savedPet);
    }

//    public PetResponse createPet(PetRequest petRequest) {
//        log.info("Iniciando a criação de um novo pet.");
//        User user = authenticationService.getCurrentUser(); // Obtém o usuário autenticado
//        Pet pet = PetMapper.petMapper().toEntity(petRequest);
//        pet.setUser(user);
//        Pet savedPet = petRepository.save(pet);
//        log.info("Pet criado com sucesso com ID: {}", savedPet.getId());
//        return PetMapper.petMapper().toResponse(savedPet);
//    }

    @Transactional
    public String updatePet(Long id, PetRequest petRequest) {

        log.info("Iniciando a atualização do pet com ID: {}", id);
        Pet existingPet = getPetOwnedByCurrentUser(id);

        if (petRequest.getName() != null) {
            existingPet.setName(petRequest.getName());
            log.debug("Nome atualizado para: {}", petRequest.getName());
        }

        if (petRequest.getAge() != 0) {
            existingPet.setAge(petRequest.getAge());
            log.debug("Idade atualizada para: {}", petRequest.getAge());
        }

        if (petRequest.getColor() != null) {
            existingPet.setColor(petRequest.getColor());
            log.debug("Cor atualizada para: {}", petRequest.getColor());
        }

        if (petRequest.getBreed() != null) {
            existingPet.setBreed(petRequest.getBreed());
            log.debug("Raça atualizada para: {}", petRequest.getBreed());
        }

        if (petRequest.getAnimalType() != null) {
            existingPet.setAnimalType(petRequest.getAnimalType());
            log.debug("Tipo de animal atualizado para: {}", petRequest.getAnimalType());
        }

        if (petRequest.getBirthdate() != null) {
            existingPet.setBirthdate(petRequest.getBirthdate());
            log.debug("Data de nascimento atualizada para: {}", petRequest.getBirthdate());
        }

        petRepository.save(existingPet);
        log.info("Pet com ID: {} atualizado com sucesso.", id);
        return "Pet atualizado com sucesso!";
    }

    @Transactional(readOnly = true)
    public PetResponse getPetById(Long id) {
        log.info("Buscando pet com ID: {}", id);
        Pet pet = getPetOwnedByCurrentUser(id);
        log.info("Pet com ID: {} encontrado.", id);
        return PetMapper.petMapper().toResponse(pet);
    }

    public List<PetResponse> getPetsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new PetNotFoundException("User not found"));
        List<Pet> pets = petRepository.findByUser(user);
        return pets.stream()
                .map(petMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetResponse> getAllPets() {
        log.info("Buscando todos os pets do usuário autenticado.");
        User user = authenticationService.getCurrentUser();
        List<Pet> pets = petRepository.findByUser(user);
        log.info("Total de {} pets encontrados.", pets.size());
        return PetMapper.petMapper().toResponseList(pets);
    }

    @Transactional
    public String deletePet(Long id) {
        log.info("Iniciando a exclusão do pet com ID: {}", id);
        Pet pet = getPetOwnedByCurrentUser(id);
        petRepository.delete(pet);
        log.info("Pet com ID: {} excluído com sucesso.", id);
        return "Pet excluído com sucesso!";
    }

    private Pet getPetOwnedByCurrentUser(Long id) {
        User user = authenticationService.getCurrentUser();
        return petRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    log.error("Pet não encontrado ou não pertence ao usuário autenticado. ID: {}", id);
                    return new PetNotFoundException(messageUtil.getMessage("petNotFound") + id);
                });
    }

}
