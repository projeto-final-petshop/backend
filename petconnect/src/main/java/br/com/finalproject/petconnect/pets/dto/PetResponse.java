package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    @JsonProperty("petId")
    private Long id;

    private String name;

    private int age;

    private String color;

    private String breed;

    private String animalType;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updatedAt;

    @JsonProperty("userId")
    private Long userId;

}
