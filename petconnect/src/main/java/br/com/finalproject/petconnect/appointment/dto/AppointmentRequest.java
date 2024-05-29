package br.com.finalproject.petconnect.appointment.dto;

import br.com.finalproject.petconnect.appointment.entities.PetType;
import br.com.finalproject.petconnect.appointment.entities.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;

}
