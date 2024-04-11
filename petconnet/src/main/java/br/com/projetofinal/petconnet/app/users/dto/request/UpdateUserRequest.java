package br.com.projetofinal.petconnet.app.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    private String username;

    @Size(min = 3)
    private String name;

    @CPF
    private String documentNumber;

    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
    private String phoneNumber;

}
