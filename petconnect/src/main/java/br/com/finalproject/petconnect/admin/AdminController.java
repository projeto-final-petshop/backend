package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admins")
@RestController
@AllArgsConstructor
@Tag(name = "Admin", description = "Cadastro de Administrador, Funcionários e Médicos Veterinários")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Cadastrar um novo Administrador")
    @ApiResponse(responseCode = "200", description = "Novo administrador cadastrado com sucesso.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @ApiResponse(responseCode = "404", description = "Role não encontrada.", content = @Content)
    @ApiResponse(responseCode = "409", description = "Email já cadastrado.", content = @Content)
    @ApiResponse(responseCode = "409", description = "CPF já cadastrado.", content = @Content)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid UserRequest registerUserDto) {
        User createdAdmin = adminService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

    @Operation(summary = "Cadastrar um novo Usuário com role específica")
    @ApiResponse(responseCode = "200", description = "Novo usuário cadastrado com sucesso.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @ApiResponse(responseCode = "400", description = "Campo obrigatório não preenchido.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Role não encontrada.", content = @Content)
    @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado.", content = @Content)
    @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para cadastrar.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro no servidor.", content = @Content)
    @PostMapping("/register/{roleCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest registerUserDto,
                                           @PathVariable(name = "roleCode") int roleCode) {
        User createdUser = adminService.createUserWithRole(registerUserDto, roleCode);
        return ResponseEntity.ok(createdUser);
    }

}