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

@Slf4j
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // TODO: substituir 'null' por mensagens customizadas.

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

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable(name = "id") Long appointmentId,
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

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable(name = "id") Long appointmentId,
                                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            log.info("Recebida solicitação de cancelamento para o agendamento ID: {}", appointmentId);
            appointmentService.cancelAppointment(appointmentId, authorizationHeader);
            log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
            return ResponseEntity.ok("Agendamento cancelado com sucesso.");
        } catch (IllegalArgumentException e) {
            log.error("Erro ao cancelar agendamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado ou não pertence ao usuário.");  // Customize response as needed
        } catch (Exception e) {
            log.error("Erro interno ao cancelar agendamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao cancelar o agendamento.");  // Customize response as needed
        }
    }

}
