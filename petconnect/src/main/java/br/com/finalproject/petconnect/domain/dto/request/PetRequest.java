package br.com.finalproject.petconnect.domain.dto.request;

import br.com.finalproject.petconnect.domain.enums.PetType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    private LocalDate birthdate;
    private String breed;
    private String color;
    private String name;
    private Long userId; // id do usuário a que o pet pertence
    private PetType petType;

}
