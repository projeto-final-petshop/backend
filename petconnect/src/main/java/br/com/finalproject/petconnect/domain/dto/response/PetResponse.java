package br.com.finalproject.petconnect.domain.dto.response;

import br.com.finalproject.petconnect.domain.enums.PetType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    private Long id;
    private LocalDate birthdate;
    private String breed;
    private String color;
    private String name;
    private UserResponse user;
    private PetType petType;

}
