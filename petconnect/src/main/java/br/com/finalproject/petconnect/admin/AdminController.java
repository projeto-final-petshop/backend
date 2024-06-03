package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
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
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid UserRequest registerUserDto) {
        User createdAdmin = adminService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

    @PostMapping("/register/{roleCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest registerUserDto,
                                           @PathVariable(name = "roleCode") int roleCode) {
        User createdUser = adminService.createUserWithRole(registerUserDto, roleCode);
        return ResponseEntity.ok(createdUser);
    }

}