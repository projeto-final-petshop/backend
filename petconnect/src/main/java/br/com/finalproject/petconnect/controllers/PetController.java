package br.com.finalproject.petconnect.controllers;

import br.com.finalproject.petconnect.domain.dto.request.PetRequest;
import br.com.finalproject.petconnect.domain.dto.request.UpdatePetRequest;
import br.com.finalproject.petconnect.domain.dto.response.PetResponse;
import br.com.finalproject.petconnect.services.impl.PetServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    private final PetServiceImpl petServiceImpl;

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Criando um novo animal de estimação.");
        PetResponse response = petServiceImpl.createPet(request, authorizationHeader);
        log.info("Novo animal de estimação criado com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> listPets(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recuperando a lista de animais de estimação.");
        List<PetResponse> response = petServiceImpl.listPets(authorizationHeader);
        log.info("Lista de animais de estimação recuperada com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetDetails(@PathVariable(name = "id") Long id,
                                                     @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recuperando detalhes do animal de estimação com ID: {}", id);
        PetResponse response = petServiceImpl.getPetDetails(id, authorizationHeader);
        log.info("Detalhes do animal de estimação recuperados com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> updatePet(@PathVariable(name = "id") Long id,
                                                 @RequestBody @Valid UpdatePetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Atualizando detalhes do animal de estimação com ID: {}", id);
        PetResponse response = petServiceImpl.updatePet(id, request, authorizationHeader);
        log.info("Detalhes do animal de estimação atualizados com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") Long id,
                                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Excluindo animal de estimação com ID: {}", id);
        petServiceImpl.deletePet(id, authorizationHeader);
        log.info("Animal de estimação excluído com sucesso.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
