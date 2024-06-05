package br.com.finalproject.petconnect.admin;

import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admins")
@RestController
@AllArgsConstructor
@CrossOrigin(
        maxAge = 36000,
        allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET})
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        log.info("Iniciando criação de um novo usuário com email: {}", request.getEmail());
        UserResponse createdUser = adminService.registerUser(request);
        log.info("Usuário criado com sucesso: {}", createdUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/all")
    public ResponseEntity<Page<UserResponse>> listAllUsers(@RequestParam(required = false) Boolean active,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "name") String sortField,
                                                           @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<UserResponse> users = adminService.listAllUsers(active, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) String cpf,
                                                          @RequestParam(required = false) Boolean active,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> users = adminService.searchUsers(name, email, cpf, active, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/appointments/all")
    public ResponseEntity<Page<AppointmentResponse>> listAllAppointments(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AppointmentResponse> appointments = adminService.listAllAppointments(pageable);
        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pets/all")
    public ResponseEntity<Page<PetResponse>> listAllPets(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PetResponse> appointments = adminService.listAllPets(pageable);
        return ResponseEntity.ok(appointments);
    }

}