package br.com.project.petconnect.pet.dto;

import lombok.*;

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

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @Past(message = "Data de nascimento deve ser no passado")
//    private LocalDate birthdate;

}
