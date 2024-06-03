package br.com.finalproject.petconnect.appointment.controllers;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.services.AppointmentService;
import br.com.finalproject.petconnect.exceptions.runtimes.pet.PetNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Appointment", description = "Agendamento de Serviços e Consultas Veterinárias")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Agendar um novo serviço ou uma nova consulta")
    @ApiResponse(
            responseCode = "200", description = "Agendamento realizado com sucesso.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "404", description = "APPOINTMENT_NOT_FOUND", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno ao criar agendamento", content = @Content)
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

    @Operation(summary = "Atualização (reagendamento) do serviço ou consulta.")
    @ApiResponse(
            responseCode = "200", description = "Agendamento atualizado com sucesso.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "404", description = "APPOINTMENT_NOT_FOUND", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar agendamento.", content = @Content)
    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                                 @RequestBody @Valid AppointmentRequest request,
                                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de atualização para o agendamento ID: {}", appointmentId);
        AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request, authorizationHeader);
        log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancelamento do agendamento de serviço ou consulta.")
    @ApiResponse(
            responseCode = "200", description = "Agendamento cancelado com sucesso.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "404", description = "APPOINTMENT_NOT_FOUND", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno ao cancelar o agendamento.", content = @Content)
    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de cancelamento para o agendamento ID: {}", appointmentId);
        appointmentService.cancelAppointment(appointmentId, authorizationHeader);
        log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
        return ResponseEntity.ok("Agendamento cancelado com sucesso.");
    }

    @Operation(summary = "Listar todos os serviços e agendamentos.")
    @ApiResponse(
            responseCode = "200", description = "Lista de Agendamento",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class, type = "array"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content)
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointmentsByUser(authorizationHeader);
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Listar agendamentos de um único Pet.")
    @ApiResponse(
            responseCode = "200", description = "Lista de Agendamento do Pet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class, type = "array"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "404", description = "PET_NOT_FOUND", content = @Content)
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content)
    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPet(
            @PathVariable(name = "petId") Long petId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPet(petId, authorizationHeader);
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Buscar (visualizar) agendamento.")
    @ApiResponse(
            responseCode = "200", description = "Detalhes do agendamento.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class, type = "object"))})
    @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content)
    @ApiResponse(responseCode = "404", description = "PET_NOT_FOUND", content = @Content)
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content)
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(
            @PathVariable(name = "appointmentId") Long appointmentId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        AppointmentResponse appointment = appointmentService.getAppointmentById(appointmentId, authorizationHeader);
        return ResponseEntity.ok(appointment);
    }

}
