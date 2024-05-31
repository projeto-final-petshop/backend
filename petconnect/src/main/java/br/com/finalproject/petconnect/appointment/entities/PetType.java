package br.com.finalproject.petconnect.appointment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetType {

    DOG(1),
    CAT(2),
    OTHER(3);

    private final int code;

}
