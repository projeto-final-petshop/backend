package br.com.finalproject.petconnect.roles.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    USER(1),
    ADMIN(2),
    EMPLOYEE(3),
    VETERINARIAN(4);

    private final int value;

    public static RoleEnum fromValue(int value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
