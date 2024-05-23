package br.com.project.petconnect.pet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PetRequest", description = "Parâmetros necessários para retornar as informações do Pet")
public class PetResponse {

    private Long id;
    private String name;
    private int age;
    private String color;
    private String breed;
    private String animalType;
    private LocalDate birthdate;
    private Long userId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;

}
