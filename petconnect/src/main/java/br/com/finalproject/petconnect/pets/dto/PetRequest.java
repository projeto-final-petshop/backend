package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    private String name;
    private int age;
    private String color;
    private String breed;
    private String animalType;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

}
