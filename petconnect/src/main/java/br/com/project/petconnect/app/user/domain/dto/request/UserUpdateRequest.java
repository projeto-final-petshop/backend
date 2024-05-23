package br.com.project.petconnect.app.user.domain.dto.request;

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
public class UserUpdateRequest {

    @Size(min = 3, message = "O nome do usuário deve ter no mínimo {min} caracteres.")
    private String name;

    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    @CPF(message = "Deve ser um número de documento do tipo CPF válido.")
    private String cpf;

    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
            message = "O número de telefone deve estar no formato E.164")
    private String phoneNumber;

}
