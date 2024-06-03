package br.com.finalproject.petconnect.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserRequest {

    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @Email(message = "Por favor, insira um endereço de e-mail válido.",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String password;

    private String confirmPassword;

    @CPF(message = "Por favor, insira um CPF válido.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Pattern(regexp = "^\\+?\\d{13,14}$",
            message = "Por favor, insira um número de telefone válido no formato E.164.")
    private String phoneNumber;

    private Boolean active;


    private String address;

}
