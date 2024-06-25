package br.com.finalproject.petconnect.pets.dto.request;

import br.com.finalproject.petconnect.domain.enums.PetType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotNull(message = "Nome do Pet é obrigatório.")
    private String name;

    @NotNull(message = "Cor do Pet é obrigatório.")
    private String color;

    @NotNull(message = "Raça do Pet é obrigatório.")
    private String breed;

    @NotNull(message = "Tipo de animal é obrigatório.")
    private PetType petType;

    @NotNull(message = "Data de nascimento do Pet é obrigatório.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

}
