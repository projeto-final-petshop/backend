package br.com.finalproject.petconnect.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Schema(
            name = "email",
            type = "String",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário",
            example = "username@domain.com"
    )
    @Email
    private String email;

    @Schema(
            name = "name",
            type = "String",
            minLength = 3,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Usuário"
    )
    private String name;

    @Schema(
            name = "cpf",
            type = "String",
            minLength = 11,
            maxLength = 11,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do Usuário. Deve ser um número válido"
    )
    @CPF
    private String cpf;

    @Schema(
            name = "phoneNumber",
            type = "String",
            minLength = 13,
            maxLength = 14,
            description = "Número de telefone do Usuário"
    )
    private String phoneNumber;



}
