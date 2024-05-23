package br.com.project.petconnect.app.user.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Classe responsável por receber os dados do usuário
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Size(min = 3, message = "O nome do usuário deve ter no mínimo {min} caracteres.")
    private String name;

    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    @CPF(message = "Deve ser um número de documento do tipo CPF válido.")
    private String cpf;

    // Para incluir um novo campo basta tira o comentário abaixo.
//    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
//            message = "O número de telefone deve estar no formato E.164")
//    private String phoneNumber;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, " +
                    "uma letra minúscula, um número e um caractere especial.")
    private String password;

    private String confirmPassword;

}
