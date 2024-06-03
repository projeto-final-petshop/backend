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

    private Long petId;

    private ServiceType serviceType;

    private PetType petType;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime appointmentTime;

}
