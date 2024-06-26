package br.com.finalproject.petconnect.user.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.user.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/me", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserRequest request)
            throws InvalidRequestException, ResourceNotFoundException, ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        try {
            userService.updateUser(currentPrincipalName, request);
            log.info("Usuário atualizado com sucesso: {}", currentPrincipalName);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException e) {
            log.error("Erro ao atualizar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deactivate", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deactivateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        try {
            userService.deactivateUser(currentPrincipalName);
            log.info("Usuário desativado com sucesso: {}", currentPrincipalName);
            return ResponseEntity.ok("Usuário desativado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException e) {
            log.error("Erro ao desativar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
