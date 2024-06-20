package br.com.finalproject.petconnect.appointment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppointmentStatus {

    SCHEDULED(1),
    CANCELLED(2),
    COMPLETED(3),
    PENDING(4),
    CONFIRMED(5);

    private final int code;

}
