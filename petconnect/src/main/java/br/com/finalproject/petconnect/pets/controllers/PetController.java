package br.com.finalproject.petconnect.pets.controllers;

import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.services.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest request) {
        PetResponse response = petService.createPet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updatePet(@PathVariable Long id,
                                            @RequestBody PetRequest request) {
        String message = petService.updatePet(id, request);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        PetResponse response = petService.getPetById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        List<PetResponse> response = petService.getAllPets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        String message = petService.deletePet(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
