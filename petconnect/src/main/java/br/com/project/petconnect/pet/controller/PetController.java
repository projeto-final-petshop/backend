package br.com.project.petconnect.pet.controller;

import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.service.PetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponse> cadastrar(@RequestBody PetRequest request) {
        PetResponse response = petService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<PetResponse> atualizar(@RequestBody PetRequest request) {
        PetResponse response = petService.atualizar(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> buscarPorId(@PathVariable Long id) {
        PetResponse response = petService.buscarPetPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<PetResponse> buscarPorNome(@PathVariable String name) {
        PetResponse response = petService.buscarPetPorNome(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> listar() {
        List<PetResponse> response = petService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
