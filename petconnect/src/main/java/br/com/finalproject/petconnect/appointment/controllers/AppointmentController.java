package br.com.finalproject.petconnect.appointment.controllers;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.services.AppointmentService;
import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<AppointmentResponse> scheduleAppointment(@RequestBody @Valid AppointmentRequest request,
                                                                   @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            log.info("Recebida solicitação de agendamento para o pet ID: {}", request.getPetId());
            AppointmentResponse response = appointmentService.scheduleAppointment(request, authorizationHeader);
            log.info("Agendamento criado com sucesso. Agendamento ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (PetNotFoundException e) {
            log.error("Erro ao criar agendamento: pet não encontrado. Pet ID: {}", request.getPetId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Erro interno ao criar agendamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                                 @RequestBody @Valid AppointmentRequest request,
                                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            log.info("Recebida solicitação de atualização para o agendamento ID: {}", appointmentId);
            AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request, authorizationHeader);
            log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Erro ao atualizar agendamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Erro interno ao atualizar agendamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            log.info("Recebida solicitação de cancelamento para o agendamento ID: {}", appointmentId);
            appointmentService.cancelAppointment(appointmentId, authorizationHeader);
            log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
            return ResponseEntity.ok("Agendamento cancelado com sucesso.");
        } catch (IllegalArgumentException e) {
            log.error("Erro ao cancelar agendamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado ou não pertence ao usuário.");
        } catch (Exception e) {
            log.error("Erro interno ao cancelar agendamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao cancelar o agendamento.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointmentsByUser(authorizationHeader);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPet(
            @PathVariable(name = "petId") Long petId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPet(petId, authorizationHeader);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(
            @PathVariable(name = "appointmentId") Long appointmentId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        AppointmentResponse appointment = appointmentService.getAppointmentById(appointmentId, authorizationHeader);
        return ResponseEntity.ok(appointment);
    }

}
