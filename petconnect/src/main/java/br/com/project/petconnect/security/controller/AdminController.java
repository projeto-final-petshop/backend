package br.com.project.petconnect.security.controller;

import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "Super Administrador")
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cadastrar Administrador")
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody UserRequest userRequest) {
        User createdAdmin = userService.createAdministrator(userRequest);
        return ResponseEntity.ok(createdAdmin);
    }

}
