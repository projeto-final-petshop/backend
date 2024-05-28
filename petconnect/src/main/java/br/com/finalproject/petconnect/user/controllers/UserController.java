package br.com.finalproject.petconnect.user.controllers;

import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.services.UserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User response = (User) authentication.getPrincipal();
        log.info("Usuário autenticado recuperado: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List<User> response = userService.allUsers();
        log.info("Todos os usuários recuperados: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<User> findUser(FindUserRequest request) {
        User user = userService.findUser(request);
        log.info("Usuário encontrado: {}", user != null ? user : "Nenhum usuário encontrado para a pesquisa.");
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsersByName(@RequestParam String name) {
        List<User> response = userService.listUsersByName(name);
        log.info("Usuários listados por nome ({}): {}", name, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> listActiveUsers() {
        List<User> response = userService.listActiveUsers();
        log.info("Usuários ativos listados: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<User>> listInactiveUsers() {
        List<User> response = userService.listInactiveUsers();
        log.info("Usuários inativos listados: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User response = userService.getUserById(id);
            log.info("Usuário recuperado pelo ID ({}): {}", id, response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException ex) {
            log.warn("Usuário não encontrado pelo ID ({}): {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest userUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            userService.updateUser(currentPrincipalName, userUpdateRequest);
            return ResponseEntity.ok("User updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        try {
            String response = userService.deactivateUser(id);
            log.info("Usuário desativado (ID: {}): {}", id, response);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (UsernameNotFoundException ex) {
            log.warn("Erro ao desativar usuário (ID: {}): {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {
        try {
            String response = userService.activateUser(id);
            log.info("Usuário ativado (ID: {}): {}", id, response);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (UsernameNotFoundException ex) {
            log.warn("Erro ao ativar usuário (ID: {}): {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            userService.deleteUser(currentPrincipalName);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
