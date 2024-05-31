package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.user.dto.request.RegisterUserRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.INVALID_INPUT_DATA;

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
    @ApiResponse(responseCode = "400", description = INVALID_INPUT_DATA, content = @Content)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid RegisterUserRequest registerUserDto) {
        User createdAdmin = adminService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

}
