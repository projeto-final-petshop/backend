package br.com.project.petconnect.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String name;

    @Email
    private String email;

    private String password;

    @CPF
    private String cpf;

    private String phoneNumber;

}
