package br.com.project.petconnect.app.pet.domain.dto;

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

    private Long id;

    private String name;

    //private String age;

    private String color;

    private String breed;

    @JsonProperty("animal_type")
    private String animalType;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

}
