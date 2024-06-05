package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "Cadastro de Administrador, Funcionários e Médicos Veterinários")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequestMapping("/admins")
@RestController
@AllArgsConstructor
@CrossOrigin(
        maxAge = 36000,
        allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST})
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Cria um novo administrador",
            description = "Endpoint para criar um novo administrador",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid UserRequest registerUserDto) {
        log.info("Iniciando criação de um novo administrador");
        User createdAdmin = adminService.createAdministrator(registerUserDto);
        log.info("Administrador criado com sucesso: {}", createdAdmin);
        return ResponseEntity.ok(createdAdmin);
    }

    @Operation(summary = "Cria um novo usuário com um código de papel específico",
              description = "Endpoint para criar um novo usuário com base no código do papel fornecido",
              security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping("/register/{roleCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest registerUserDto,
                                           @PathVariable(name = "roleCode") int roleCode) {
        log.info("Iniciando criação de um novo usuário com código de papel: {}", roleCode);
        User createdUser = adminService.createUserWithRole(registerUserDto, roleCode);
        log.info("Usuário criado com sucesso: {}", createdUser);
        return ResponseEntity.ok(createdUser);
    }

}