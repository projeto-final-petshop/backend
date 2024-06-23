package br.com.finalproject.petconnect.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Campo nome é obrigatório.")
    @Size(min = 3, max = 250, message = "Nome: O nome deve ter entre {min} e {max} caracteres.")
    private String name;

    @NotNull(message = "Campo email é obrigatório.")
    @Email(message = "Email: Insira um email válido.", regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "Campo senha é obrigatório.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "Senha: A senha deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Campo confirmar senha é obrigatório.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @NotNull(message = "Campo CPF é obrigatório.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "CPF: Por favor, insira um CPF válido.")
    private String cpf;

    @Pattern(regexp = "^\\+?\\d{9,14}$",
            message = "Número de Telefone: Por favor, insira um número de telefone que tenha entre 9 e 14 dígitos numéricos. O sinal de + é opcional.")
    private String phoneNumber;

    @Size(min = 10, max = 250, message = "Endereço: O endereço deve ter entre {min} e {max} caracteres.")
    private String address;

    private Long role;

}
