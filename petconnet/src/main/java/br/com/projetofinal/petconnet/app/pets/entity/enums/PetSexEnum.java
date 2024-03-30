package br.com.projetofinal.petconnet.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetSexEnum {

    /**
     * PT: Macho
     * <p>
     * EN: Male
     */
    MALE("Male"),
    /**
     * PT: Fêmea
     * <p>
     * EN: Female
     */
    FEMALE("Female");

    private final String description;

}
