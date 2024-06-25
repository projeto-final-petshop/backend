package br.com.finalproject.petconnect.pets.dto.request;

import br.com.finalproject.petconnect.domain.enums.PetType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePetRequest {

    private String name;
    private String color;
    private String breed;
    private PetType petType;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

}
