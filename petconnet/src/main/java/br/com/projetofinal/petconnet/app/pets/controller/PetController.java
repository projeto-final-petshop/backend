package br.com.projetofinal.petconnet.app.pets.controller;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.app.pets.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
@CrossOrigin("*")
public class PetController {

    private final PetService petService;

    @PostMapping(value = "/register")
    public ResponseEntity<PetResponse> registerPet(@RequestBody PetRequest request) {
        PetResponse response = petService.registerPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/search/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> searchPetById(@PathVariable(name = "id") Long id) {
        PetResponse response = petService.searchPetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetResponse>> listAllPets() {
        List<PetResponse> response = petService.listAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> updatePetById(@PathVariable(name = "id") Long id,
                                                     @RequestBody PetRequest request) {
        petService.updatePetById(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePetById(@PathVariable(name = "id") Long id) {
        petService.removePetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
