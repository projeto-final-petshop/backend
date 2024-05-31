package br.com.finalproject.petconnect.appointment.dto;

import br.com.finalproject.petconnect.appointment.entities.PetType;
import br.com.finalproject.petconnect.appointment.entities.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @Schema(name = "petId", type = "integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do pet",
            example = "10")
    private Long petId;

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
    @JsonFormat(
            pattern = "dd/MM/yyyy",
            shape = JsonFormat.Shape.STRING)
    private LocalDate appointmentDate;

    @Schema(name = "appointmentTime", type = "string",
            format = "time", pattern = "HH:mm",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10:00")
    @JsonFormat(pattern = "HH:mm",
            shape = JsonFormat.Shape.STRING)
    private LocalTime appointmentTime;

}
