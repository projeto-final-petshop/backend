package br.com.projetofinal.petconnet.app.pets.controller;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.dto.request.PetUpdateRequest;
import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.app.pets.service.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
@CrossOrigin("*")
public class PetController {

    private final PetService petService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest request) {
        log.info("Pet Controller --- Recebendo requisição para cadastrar pet");
        PetResponse response = petService.createPet(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> searchPetById(@PathVariable(name = "id") Long id) {
        PetResponse response = petService.searchPetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<PetResponse>> listAllPets() {
        List<PetResponse> response = petService.listAllPets();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<PetResponse> updatePetById(@RequestBody PetUpdateRequest request) {
        PetResponse response = petService.updatePet(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePetById(@PathVariable(name = "id") Long id) {
        petService.removePetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
