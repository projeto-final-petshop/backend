package br.com.finalproject.petconnect.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.crypto.Mac;

/**
 * NotNull - Geral: Este campo é obrigatório e não pode ser deixado em branco.
 * <p>
 * NotBlank - Geral: Este campo não pode estar em branco.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    // NotNull: Por favor, informe o seu nome.
    @NotBlank(message = "O nome não pode estar em branco. Por favor, informe o seu nome.")
    @Size(min = 3, max = 250,
            message = "Por favor, informe o seu nome completo. O nome deve ter entre {min} e {max} caracteres.")
    private String name;

    // NotNull: Por favor, informe o seu e-mail.
    @NotBlank(message = "O e-mail não pode estar em branco. Por favor, informe o seu e-mail.")
    @Email(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            message = "Por favor, informe um e-mail válido. Exemplo: seuemail@dominio.com.")
    private String email;

    // NotNull: Por favor, informe a sua senha.
    @NotBlank(message = "A senha não pode estar em branco. Por favor, informe a sua senha.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "Por favor, informe uma senha. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // NotNull: Por favor, confirme a sua senha.
    @NotBlank(message = "A confirmação da senha não pode estar em branco. Por favor, confirme a sua senha.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    // NotNull: Por favor, informe o seu CPF.
    @NotBlank(message = "O CPF não pode estar em branco. Por favor, informe o seu CPF.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, informe um CPF válido. Exemplo: 123.456.789-00.")
    @CPF(message = "Por favor, informe um CPF válido.")
    private String cpf;

    // NotBlank: O número de telefone não pode estar em branco. Por favor, informe o seu número de telefone.
    // NotNull: Por favor, informe o seu número de telefone.
    @Pattern(regexp = "^\\+?\\d{9,14}$",
            message = "Insira um número de telefone que tenha entre 9 e 14 dígitos numéricos. O sinal de + é opcional.")
    private String phoneNumber;

    // NotBlank: O endereço não pode estar em branco. Por favor, informe o seu endereço.
    // NotNull: Por favor, informe o seu endereço
    @Size(min = 10, max = 250,
            message = "O endereço deve ter entre {min} e {max} caracteres.")
    private String address;

    // NotBlank: O endereço não pode estar em branco. Por favor, informe o seu endereço.
    // NotNull: Por favor, informe o seu papel do usuário
    private Long role;

}
