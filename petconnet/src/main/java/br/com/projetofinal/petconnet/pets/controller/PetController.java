package br.com.projetofinal.petconnet.pets.controller;

import br.com.projetofinal.petconnet.pets.dto.PetRequest;
import br.com.projetofinal.petconnet.pets.dto.PetResponse;
import br.com.projetofinal.petconnet.pets.service.PetService;
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
        PetResponse response = petService.createPet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PetResponse>> listPets() {
        List<PetResponse> response = petService.getAllPets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
