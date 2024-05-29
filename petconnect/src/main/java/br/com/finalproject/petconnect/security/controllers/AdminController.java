package br.com.finalproject.petconnect.security.controllers;

import br.com.finalproject.petconnect.security.services.AuthenticationService;
import br.com.finalproject.petconnect.user.dto.RegisterUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
@AllArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid RegisterUserRequest registerUserDto) {
        User createdAdmin = authenticationService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

}
