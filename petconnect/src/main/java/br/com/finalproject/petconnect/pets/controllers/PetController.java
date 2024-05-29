package br.com.finalproject.petconnect.pets.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.services.PetService;
import br.com.finalproject.petconnect.security.services.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final JwtService jwtService;
    private final PetService petService;

    @PostMapping("/create")
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest request,
                                                 @RequestHeader("Authorization") String authorization) {
        try {
            PetResponse response = petService.createPet(request, authorization);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetResponse>> listPets(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            List<PetResponse> response = petService.listPets(authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetDetails(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String authorizationHeader) {
        try {
            PetResponse response = petService.getPetDetails(id, authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long id,
                                                 @RequestBody PetRequest request,
                                                 @RequestHeader("Authorization") String token) {
        try {
            PetResponse response = petService.updatePet(id, request, token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id,
                                          @RequestHeader("Authorization") String token) {
        try {
            petService.deletePet(id, token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException | PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
