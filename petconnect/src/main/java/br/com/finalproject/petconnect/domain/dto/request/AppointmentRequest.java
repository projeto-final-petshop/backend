package br.com.finalproject.petconnect.domain.dto.request;

import br.com.finalproject.petconnect.domain.enums.ServiceType;
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

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Long petId; // id do pet associado ao agendamento
    private Long userId; // id do usuário que fez o agendamento
    private ServiceType serviceType;

}