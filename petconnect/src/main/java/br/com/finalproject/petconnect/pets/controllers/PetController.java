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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest request) {
        log.info("Iniciando criação de novo pet com dados: {}", request);
        PetResponse response = petService.createPet(request);
        log.info("Pet criado com sucesso: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updatePet(@PathVariable Long id,
                                            @RequestBody PetRequest request) {
        log.info("Iniciando atualização do pet com ID: {}. Dados: {}", id, request);
        String message = petService.updatePet(id, request);
        log.info("Pet atualizado com sucesso. ID: {}, Mensagem: {}", id, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        log.info("Recuperando informações do pet com ID: {}", id);
        PetResponse response = petService.getPetById(id);
        log.info("Informações do pet recuperadas: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        log.info("Recuperando informações de todos os pets");
        List<PetResponse> response = petService.getAllPets();
        log.info("Informações de todos os pets recuperadas: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        log.info("Iniciando exclusão do pet com ID: {}", id);
        String message = petService.deletePet(id);
        log.info("Pet excluído com sucesso. ID: {}, Mensagem: {}", id, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
