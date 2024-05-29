package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.mapping.PetMapper;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.security.services.JwtService;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import br.com.finalproject.petconnect.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final JwtService jwtService;
    private final MessageUtil messageUtil;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public PetResponse createPet(PetRequest petRequest, String authorizationHeader) {

        try {

            String token = extractToken(authorizationHeader);
            String userEmail = jwtService.extractUsername(token);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));

            Pet pet = PetMapper.petMapper().toEntity(petRequest);
            pet.setUser(user);

            Pet savedPet = petRepository.save(pet);
            return PetMapper.petMapper().toResponse(savedPet);

        } catch (Exception e) {
            throw new PetNotFoundException("Falha ao cadastrar Pet.", e);
        }

    }

    public List<PetResponse> listPets(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));

        List<Pet> pets = petRepository.findByUser(user);
        return PetMapper.petMapper().toResponseList(pets);
    }

    public PetResponse getPetDetails(Long petId, String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));

        Pet pet = petRepository.findByIdAndUser(petId, user)
                .orElseThrow(() -> new PetNotFoundException(messageUtil.getMessage("petNotFound")));

        return PetMapper.petMapper().toResponse(pet);
    }

    public PetResponse updatePet(Long petId, PetRequest petRequest, String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));

        Pet existingPet = petRepository.findByIdAndUser(petId, user)
                .orElseThrow(() -> new PetNotFoundException(messageUtil.getMessage("petNotFound")));

        existingPet.setName(petRequest.getName());
        existingPet.setAge(petRequest.getAge());
        existingPet.setColor(petRequest.getColor());
        existingPet.setBreed(petRequest.getBreed());
        existingPet.setAnimalType(petRequest.getAnimalType());
        existingPet.setBirthdate(petRequest.getBirthdate());

        petRepository.save(existingPet);
        return PetMapper.petMapper().toResponse(existingPet);
    }

    public void deletePet(Long petId, String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("userNotFound")));

        Pet existingPet = petRepository.findByIdAndUser(petId, user)
                .orElseThrow(() -> new PetNotFoundException(messageUtil.getMessage("petNotFound")));

        petRepository.delete(existingPet);
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7); // Remove "Bearer " do token
    }


//    private String extractToken(String authorizationHeader) {
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            throw new IllegalArgumentException("");
//        }
//        return authorizationHeader.substring(7); // Remove "Bearer " prefix
//    }

}
