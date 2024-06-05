package br.com.finalproject.petconnect.appointment.controllers;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(maxAge = 36000, allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/schedule")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> scheduleAppointment(@RequestBody @Valid AppointmentRequest request,
                                                                   @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de agendamento para o pet ID: {}", request.getPetId());
        AppointmentResponse response = appointmentService.scheduleAppointment(request, authorizationHeader);
        log.info("Agendamento criado com sucesso. Agendamento ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{appointmentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                                 @RequestBody @Valid AppointmentRequest request,
                                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de atualização para o agendamento ID: {}", appointmentId);
        AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request, authorizationHeader);
        log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{appointmentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> cancelAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de cancelamento para o agendamento ID: {}", appointmentId);
        appointmentService.cancelAppointment(appointmentId, authorizationHeader);
        log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
        return ResponseEntity.ok("Agendamento cancelado com sucesso.");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação para listar todos os agendamentos do usuário");
        List<AppointmentResponse> appointments = appointmentService.getAllAppointmentsByUser(authorizationHeader);
        log.info("Lista de agendamentos obtida com sucesso");
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/pets/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPet(
            @PathVariable(name = "petId") Long petId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação para listar agendamentos do pet ID: {}", petId);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPet(petId, authorizationHeader);
        log.info("Lista de agendamentos do pet ID {} obtida com sucesso", petId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> getAppointmentById(
            @PathVariable(name = "appointmentId") Long appointmentId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação para obter detalhes do agendamento ID: {}", appointmentId);
        AppointmentResponse appointment = appointmentService.getAppointmentById(appointmentId, authorizationHeader);
        log.info("Detalhes do agendamento ID {} obtidos com sucesso", appointmentId);
        return ResponseEntity.ok(appointment);
    }

}
