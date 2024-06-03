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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Agendar um novo serviço ou consulta",
            description = "Endpoint para agendar um novo serviço ou consulta veterinária",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/schedule")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> scheduleAppointment(@RequestBody @Valid AppointmentRequest request,
                                                                   @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de agendamento para o pet ID: {}", request.getPetId());
        AppointmentResponse response = appointmentService.scheduleAppointment(request, authorizationHeader);
        log.info("Agendamento criado com sucesso. Agendamento ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar um agendamento existente",
            description = "Endpoint para atualizar um agendamento veterinário existente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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

    @Operation(summary = "Cancelar um agendamento",
            description = "Endpoint para cancelar um agendamento veterinário",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/cancel/{appointmentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> cancelAppointment(@PathVariable(name = "appointmentId") Long appointmentId,
                                                    @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação de cancelamento para o agendamento ID: {}", appointmentId);
        appointmentService.cancelAppointment(appointmentId, authorizationHeader);
        log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
        return ResponseEntity.ok("Agendamento cancelado com sucesso.");
    }

    @Operation(summary = "Listar todos os agendamentos do usuário",
            description = "Endpoint para listar todos os agendamentos do usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos obtida com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("Recebida solicitação para listar todos os agendamentos do usuário");
        List<AppointmentResponse> appointments = appointmentService.getAllAppointmentsByUser(authorizationHeader);
        log.info("Lista de agendamentos obtida com sucesso");
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Listar agendamentos por pet",
            description = "Endpoint para listar agendamentos de um pet específico",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos obtida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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

    @Operation(summary = "Obter detalhes de um agendamento",
            description = "Endpoint para obter detalhes de um agendamento específico",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do agendamento obtidos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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
