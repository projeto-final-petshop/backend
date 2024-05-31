package br.com.finalproject.petconnect.roles.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    USER(1),
    ADMIN(2),
    GROOMING(3),
    VETERINARIAN(4),
    EMPLOYEE(5);

    private final int code;

    public static RoleEnum fromValue(int value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getCode() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}