package br.com.project.petconnect.pet.controller;

import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pet", description = "Operações relacionadas aos Animais de Estimação")
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Cadastrar Pet")
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PetResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> registerPet(@RequestBody PetRequest request) {
        PetResponse response = petService.registerPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar Pet")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = PetResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetById(@PathVariable(name = "id") Long id) {
        PetResponse response = petService.getPetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Listar Pet")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = PetResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> getAll() {
        List<PetResponse> response = petService.listPets();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar Pet por Usuário")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = PetResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> listPetsByUser(@PathVariable(name = "userId") Long userId) {
        List<PetResponse> response = petService.listPetsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Excluir Pet")
    @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = PetResponse.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") Long id) {
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO: Implementar método para atualizar pet updatePet - PUT /pets/{id}

    // TODO: Implementar método para buscar pets de um usuário getUserAndPets - GET /users/{id}/pets

}
