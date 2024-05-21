package br.com.project.petconnect.app.pet.domain.dto;

import br.com.project.petconnect.app.pet.domain.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    private String name;

    private String species;

    private String breed;

    private Sex sex;

    @PositiveOrZero
    private Integer age;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthdate;

    private String size; // porte

    private Boolean neutered; // cadastrado

    private Boolean vaccination;

    private byte[] photo;

    private String description;

    private Long userId;

}
