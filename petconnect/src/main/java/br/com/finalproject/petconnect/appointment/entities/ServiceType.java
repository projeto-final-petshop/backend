package br.com.finalproject.petconnect.appointment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {

    BATH(1),
    GROOMING(2),
    BATH_AND_GROOMING(3),
    VETERINARY_CONSULTATION(4);

    private final int code;

}
