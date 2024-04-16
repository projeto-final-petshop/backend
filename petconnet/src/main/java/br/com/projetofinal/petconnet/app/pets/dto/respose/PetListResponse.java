package br.com.projetofinal.petconnet.app.pets.dto.respose;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetListResponse {

    private Long id;
    private String name;
    private String breed;
    private String color;
    private String animalType;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
