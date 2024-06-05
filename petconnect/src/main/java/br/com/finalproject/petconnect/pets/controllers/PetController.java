package br.com.finalproject.petconnect.pets.controllers;

import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name = "Pets", description = "Operações relacionadas a animais de estimação")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
@CrossOrigin(
        maxAge = 36000,
        allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class PetController {

    private final PetService petService;

    @Operation(summary = "Criar Pet",
            description = "Cria um novo registro de animal de estimação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Criando um novo animal de estimação.");
        PetResponse response = petService.createPet(request, authorizationHeader);
        log.info("Novo animal de estimação criado com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar Pets",
            description = "Lista todos os animais de estimação do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pets recuperada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> listPets(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recuperando a lista de animais de estimação.");
        List<PetResponse> response = petService.listPets(authorizationHeader);
        log.info("Lista de animais de estimação recuperada com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obter Detalhes do Pet",
            description = "Obtém os detalhes de um animal de estimação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do pet recuperados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @GetMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetDetails(@PathVariable(name = "petId") Long petId,
                                                     @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recuperando detalhes do animal de estimação com ID: {}", petId);
        PetResponse response = petService.getPetDetails(petId, authorizationHeader);
        log.info("Detalhes do animal de estimação recuperados com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Listar Todos os Pets",
            description = "Lista todos os animais de estimação cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pets recuperada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/list-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        log.info("Recuperando todos os animais de estimação.");
        List<PetResponse> pets = petService.getAllPets();
        log.info("Todos os animais de estimação recuperados com sucesso.");
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @Operation(summary = "Obter Pet pelo ID (Admin)",
            description = "Obtém um animal de estimação pelo ID, apenas para administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do pet recuperados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @GetMapping("/admin/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long petId) {
        log.info("Recuperando detalhes do animal de estimação com ID: {}", petId);
        PetResponse pet = petService.getPetById(petId);
        log.info("Detalhes do animal de estimação recuperados com sucesso.");
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Pet",
            description = "Atualiza os detalhes de um animal de estimação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @PutMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetResponse> updatePet(@PathVariable(name = "petId") Long petId,
                                                 @RequestBody @Valid PetRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Atualizando detalhes do animal de estimação com ID: {}", petId);
        PetResponse response = petService.updatePet(petId, request, authorizationHeader);
        log.info("Detalhes do animal de estimação atualizados com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Excluir Pet",
            description = "Exclui um animal de estimação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @DeleteMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "petId") Long petId,
                                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Excluindo animal de estimação com ID: {}", petId);
        petService.deletePet(petId, authorizationHeader);
        log.info("Animal de estimação excluído com sucesso.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
