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
@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/schedule")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid AppointmentRequest request,
                                                                 @RequestHeader(name = HttpHeaders
                                                                         .AUTHORIZATION) String authorizationHeader) {
        AppointmentResponse response = appointmentService.scheduleAppointment(request, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id,
                                                                 @RequestBody @Valid AppointmentRequest request,
                                                                 @RequestHeader(name = HttpHeaders
                                                                         .AUTHORIZATION) String authorizationHeader) {
        AppointmentResponse response = appointmentService.updateAppointment(id, request, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id,
                                                    @RequestHeader(name = HttpHeaders
                                                            .AUTHORIZATION) String authorizationHeader) {
        appointmentService.cancelAppointment(id, authorizationHeader);
        return ResponseEntity.ok("Agendamento cancelado com sucesso.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long id,
                                                              @RequestHeader(name = HttpHeaders
                                                                      .AUTHORIZATION) String authorizationHeader) {
        AppointmentResponse response = appointmentService.getAppointmentById(id, authorizationHeader);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/pet/{petId}")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByPet(@PathVariable Long petId,
                                                                           @RequestHeader(name = HttpHeaders
                                                                                   .AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> responses = appointmentService.getAppointmentsByPet(petId, authorizationHeader);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all")
    public ResponseEntity<List<AppointmentResponse>> listAllAppointments(@RequestHeader(name = HttpHeaders
            .AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> responses = appointmentService.getAllAppointmentsByUser(authorizationHeader);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
