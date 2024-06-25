package br.com.finalproject.petconnect.controllers;

import br.com.finalproject.petconnect.domain.dto.request.LoginRequest;
import br.com.finalproject.petconnect.domain.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.dto.response.LoginResponse;
import br.com.finalproject.petconnect.domain.dto.response.UserResponse;
import br.com.finalproject.petconnect.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.loginUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserDetails() {
        UserResponse response = userService.getCurrentUserDetails();
        return ResponseEntity.ok(response);
    }

    /**
     * Garante que o usuário só pode atualizar seu próprio perfil
     */
    @PutMapping("/update")
    @PreAuthorize("#email == authentication.principal.username")
    public ResponseEntity<Void> updateUser(@RequestParam(name = "email") String email,
                                           @Valid @RequestBody UpdateUserRequest updateRequest) {
        userService.updateUser(email, updateRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * Garante que o usuário só pode desativar sua própria conta
     */
    @DeleteMapping("/deactivate")
    @PreAuthorize("#email == authentication.principal.username")
    public ResponseEntity<Void> deactivateUser(@RequestParam(name = "email") String email) {
        userService.deactivateUser(email);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping(value = "/me", produces = "application/json")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<User> authenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        return ResponseEntity.ok(currentUser);
//    }
//
//    @PutMapping(value = "/update")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        userServiceImpl.updateUser(currentPrincipalName, request);
//        log.info("Usuário atualizado com sucesso: {}", currentPrincipalName);
//        return ResponseEntity.ok("Usuário atualizado com sucesso!");
//    }
//
//    @DeleteMapping(value = "/deactivate")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<String> deactivateUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        userServiceImpl.deactivateUser(currentPrincipalName);
//        log.info("Usuário desativado com sucesso: {}", currentPrincipalName);
//        return ResponseEntity.ok("Usuário desativado com sucesso!");
//    }

}
