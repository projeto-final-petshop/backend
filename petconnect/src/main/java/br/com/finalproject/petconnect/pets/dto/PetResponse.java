package br.com.finalproject.petconnect.pets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    @Schema(name = "appointmentId", type = "integer", format = "int64",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do Animal de Estimação",
            example = "10")
    @JsonProperty("petId")
    private Long id;

    @Schema(name = "name", type = "String",
            minLength = 3, maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Animal de Estimação",
            example = "Luna")
    private String name;

    @Schema(name = "age", type = "int", format = "int32",
            minimum = "0", description = "Idade do Animal de Estimação",
            example = "2")
    private int age;

    @Schema(name = "color", type = "String",
            description = "Cor do Animal de Estimação",
            example = "Preto")
    private String color;

    @Schema(name = "breed", type = "String",
            description = "Raça do Animal de Estimação",
            example = "Siamese")
    private String breed;

    @Schema(name = "animalType", type = "String",
            description = "Tipo de Animal de Estimação",
            example = "CAT")
    private String animalType;

    @Schema(name = "birthdate", type = "string",
            format = "date", pattern = "dd/MM/yyyy",
            description = "Data de nascimento do Pet",
            example = "10/01/2022")
    @Past(message = "A data de nascimento do pet deve estar no passado.")
    private LocalDate birthdate;

    @Schema(name = "userId", type = "integer", format = "int64",
            ref = "UserResponse.class",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Identificação única do Usuário",
            example = "10")
    @JsonProperty("userId")
    private Long userId;

    @Schema(name = "createdAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Criação")
    private OffsetDateTime createdAt;

    @Schema(name = "updatedAt", type = "String", format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Data de Atualização")
    private OffsetDateTime updatedAt;

}
