package br.com.project.petconnect.user.controller;

import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.dto.UserResponse;
import br.com.project.petconnect.user.entities.User;
import br.com.project.petconnect.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Listar informações do Usuário autenticado")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        log.info("Obtendo usuário autenticado...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        log.info("Usuário autenticado obtido com sucesso: {}", currentUser.getUsername());
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Lista todos os usuários autenticados")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        log.info("Obtendo todos os usuários autenticados...");
        List<User> users = userService.allUsers();
        log.info("Usuários autenticados obtidos com sucesso. Quantidad: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(name = "userId") Long userId,
                                             @RequestBody UserRequest request) {
        userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable(name = "username") String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}/pets")
    public ResponseEntity<UserResponse> getUserAndPets(@PathVariable(name = "userId") Long userId) {
        UserResponse response = userService.getUserAndPets(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
