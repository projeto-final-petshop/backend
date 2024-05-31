package br.com.finalproject.petconnect.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindUserRequest {

    @Schema(name = "name", type = "String", minLength = 3, maxLength = 250,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Usuário", example = "Maria Caroline Gabrielly Peixoto")
    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @Schema(name = "email", type = "String",
            pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do Usuário", example = "username@domain.com")
    @Email(message = "Por favor, insira um endereço de e-mail válido.",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Schema(name = "cpf", type = "String", minLength = 11, maxLength = 11,
            pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Inserir um número de CPF válido e no formato XXX.XXX.XXX-XX",
            example = "165.625.159-04")
    @CPF(message = "Por favor, insira um CPF válido.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Schema(name = "active", type = "boolean",
            requiredMode = Schema.RequiredMode.AUTO,
            description = "Informa se o usuário está ativo ou não no sistema",
            example = "true")
    private boolean active;

}
