package br.com.project.petconnect.app.pet.controller;

import br.com.project.petconnect.app.pet.service.PetService;
import br.com.project.petconnect.app.pet.domain.dto.PetRequest;
import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/register")
    public ResponseEntity<PetResponse> registerPet(@RequestBody PetRequest request) {
        PetResponse response = petService.registerPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable(name = "id") Long id,
                                                 @RequestBody PetRequest request) {
        PetResponse response = petService.updatePet(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable(name = "id") Long id) {
        PetResponse response = petService.getPetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAll() {
        List<PetResponse> response = petService.listPets();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") Long id) {
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
