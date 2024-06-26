package br.com.finalproject.petconnect.appointment.dto;

import br.com.finalproject.petconnect.appointment.entities.enums.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.entities.enums.ServiceType;
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

    @JsonProperty("appointmentId")
    private Long id;

    private Long petId;

    private Long userId;

    private ServiceType serviceType;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime appointmentTime;

    private AppointmentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime updatedAt;

}