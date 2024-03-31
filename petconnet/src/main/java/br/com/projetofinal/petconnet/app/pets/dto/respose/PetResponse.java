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

    private Long petId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
