package br.com.finalproject.petconnect.pets.controllers;

import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.services.PetService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pets", description = "Animal de Estimação")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        PetResponse response = petService.createPet(request, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> listPets(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<PetResponse> response = petService.listPets(authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetDetails(@PathVariable(name = "petId") Long petId,
                                                     @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        PetResponse response = petService.getPetDetails(petId, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        List<PetResponse> pets = petService.getAllPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/admin/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long petId) {
        PetResponse pet = petService.getPetById(petId);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @PutMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> updatePet(@PathVariable(name = "petId") Long petId,
                                                 @RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        PetResponse response = petService.updatePet(petId, request, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "petId") Long petId,
                                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        petService.deletePet(petId, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
