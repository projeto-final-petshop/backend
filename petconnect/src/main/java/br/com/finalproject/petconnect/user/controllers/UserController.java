package br.com.finalproject.petconnect.user.controllers;

import br.com.finalproject.petconnect.user.dto.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.UpdateUserRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User response = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> response = userService.allUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<User> findUser(FindUserRequest request) {
        User user = userService.findUser(request);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsersByName(@RequestParam String name) {
        List<User> response = userService.listUsersByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> listActiveUsers() {
        List<User> response = userService.listActiveUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<User>> listInactiveUsers() {
        List<User> response = userService.listInactiveUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        String response = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        String response = userService.deactivateUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {
        String response = userService.activateUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String response = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}