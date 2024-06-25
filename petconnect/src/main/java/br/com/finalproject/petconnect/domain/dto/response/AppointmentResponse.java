package br.com.finalproject.petconnect.domain.dto.response;

import br.com.finalproject.petconnect.domain.enums.AppointmentStatus;
import br.com.finalproject.petconnect.domain.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private PetResponse pet;
    private UserResponse user;
    private ServiceType serviceType;
    private AppointmentStatus status;

}