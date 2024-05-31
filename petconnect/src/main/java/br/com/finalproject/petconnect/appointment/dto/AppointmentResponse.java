package br.com.finalproject.petconnect.appointment.dto;

import br.com.finalproject.petconnect.appointment.entities.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.entities.PetType;
import br.com.finalproject.petconnect.appointment.entities.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    @Schema(name = "appointmentId", type = "integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do agendamento",
            example = "10")
    @JsonProperty("appointmentId")
    private Long id;

    @Schema(name = "petId", type = "integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do pet",
            example = "10")
    private Long petId;

    @Schema(name = "userId", type = "integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do Usuário",
            example = "10" )
    private Long userId;

    @Schema(name = "serviceType", enumAsRef = true,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Serviço (banho, tosa, banho e tosa) ou consulta veterinária.",
            example = "BATH_AND_GROOMING")
    private ServiceType serviceType;

    @Schema(name = "petType", enumAsRef = true,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Tipo de animal de estimação (dog, cat, outros)",
            example = "DOG")
    private PetType petType;

    @Schema(name = "appointmentDate", type = "string",
            format = "date", pattern = "dd/MM/yyyy",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10/06/2024")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;

    @Schema(name = "appointmentTime", type = "string",
            format = "time", pattern = "HH:mm",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;

    @Schema(name = "status", enumAsRef = true,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "SCHEDULED")
    private AppointmentStatus status;

    @Schema(name = "createdAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Criação")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Schema(name = "updatedAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Atualização")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updatedAt;

}
