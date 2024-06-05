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

    @Schema(
            description = "O nome do usuário",
            example = "João da Silva",
            minLength = 3,
            maxLength = 250,
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string"
    )
    @Size(min = 3, max = 250, message = "name.size.message")
    private String name;

    @Schema(
            description = "Endereço de email do usuário.",
            example = "usario@dominio.com",
            pattern = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            accessMode = Schema.AccessMode.READ_WRITE,
            type = "string",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Email(message = "email.message",
            regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

    @CPF(message = "Por favor, insira um CPF válido.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX")
    private String cpf;

    private Boolean active;

}
