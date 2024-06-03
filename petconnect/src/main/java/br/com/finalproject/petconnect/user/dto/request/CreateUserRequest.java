package br.com.finalproject.petconnect.user.dto.request;

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
public class CreateUserRequest {

    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @Email(message = "Por favor, insira um endereço de e-mail válido.")
    private String email;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter pelo menos 8 caracteres, " +
                    "incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    private String password;

    private String confirmPassword;

    @CPF(message = "Por favor, insira um CPF válido.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Pattern(regexp = "^\\+?\\d{13,14}$",
            message = "Por favor, insira um número de telefone válido no formato E.164.")
    private String phoneNumber;

    private String address;

}
