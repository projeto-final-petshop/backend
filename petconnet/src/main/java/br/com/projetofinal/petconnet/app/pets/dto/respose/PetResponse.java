package br.com.projetofinal.petconnet.app.pets.dto.respose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    private Long id;
    private String name;
    private Integer age;
    private String breed;
    private String color;
    private String species;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
