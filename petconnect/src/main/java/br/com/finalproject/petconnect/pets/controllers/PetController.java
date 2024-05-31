package br.com.finalproject.petconnect.pets.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.UserNotFoundException;
import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.*;
import static br.com.finalproject.petconnect.utils.constants.ConstantsUtil.SERVER_ERROR;

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

    @Operation(summary = "Cadastrar Pet")
    @ApiResponse(
            responseCode = "200", description = PET_CREATED_SUCCESSFULLY,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            PetResponse response = petService.createPet(request, authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Listar Pets")
    @ApiResponse(
            responseCode = "200", description = "Listar pets cadastrados do usuário",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = PET_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "404", description = USER_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PetResponse>> listPets(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            List<PetResponse> response = petService.listPets(authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            log.error("Erro ao listar Pets: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Buscar pet por ID")
    @ApiResponse(responseCode = "200", description = "Dados do pet encontrado.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = PET_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "404", description = USER_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @GetMapping("/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponse> getPetDetails(@PathVariable(name = "petId") Long petId,
                                                     @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            PetResponse response = petService.getPetDetails(petId, authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            log.error("Erro ao obter detalhes do Pet: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Listar todos os pets cadastrado.")
    @ApiResponse(responseCode = "200", description = "Lista de pets.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @GetMapping("/list-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        try {
            List<PetResponse> pets = petService.getAllPets();
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Buscar pet por ID.")
    @ApiResponse(
            responseCode = "200", description = "Pet encontrado",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = PET_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @GetMapping("/admin/{petId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long petId) {
        try {
            PetResponse pet = petService.getPetById(petId);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            log.error("Erro ao obter Pet por ID: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Atualizar Pet")
    @ApiResponse(
            responseCode = "200", description = "Pet atualizado com sucesso.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class, type = "object"))})
    @ApiResponse(responseCode = "404", description = PET_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @PutMapping("/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponse> updatePet(@PathVariable(name = "petId") Long petId,
                                                 @RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            PetResponse response = petService.updatePet(petId, request, authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | PetNotFoundException e) {
            log.error("Erro ao atualizar Pet: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Excluir Pet")
    @ApiResponse(responseCode = "200", description = "Pet excluído com sucesso.", content = @Content)
    @ApiResponse(responseCode = "404", description = PET_NOT_FOUND, content = @Content)
    @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR, content = @Content)
    @DeleteMapping("/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "petId") Long petId,
                                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            petService.deletePet(petId, authorizationHeader);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException | PetNotFoundException e) {
            log.error("Erro ao excluir Pet: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
