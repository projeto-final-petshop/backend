package br.com.finalproject.petconnect.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "CPF inválido.")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            message = "CPF inválido. Use o formato XXX.XXX.XXX-XX.")
    private String cpf;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido. Use um formato válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;

    @NotBlank(message = "A confirmação de senha é obrigatória.")
    private String confirmPassword;

    @Pattern(regexp = "^\\+?\\d{9,14}$", message = "Número de telefone inválido.")
    private String phoneNumber;

    private Boolean active = true;

    private String address;

    private Long roleId; // id da role, referência ao enum RoleType

}
