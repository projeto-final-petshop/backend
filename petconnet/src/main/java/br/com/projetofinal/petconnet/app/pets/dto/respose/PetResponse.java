package br.com.projetofinal.petconnet.app.pets.dto.respose;

import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
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
public class PetResponse {

    private Long id;
    private String name;
    private String breed;
    private String color;
    private String animalType;
    private Date birthdate;
//    private AnimalType animalType;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
