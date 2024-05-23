package br.com.project.petconnect.pet.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    private Long id;
    private String name;
    private int age;
    private String color;
    private String breed;
    private String animalType;
//    private LocalDate birthdate;
    private Long userId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;

}
