package br.com.finalproject.petconnect.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    private String email;

    private String password;

    private String name;

    @CPF
    private String cpf;

    private String phoneNumber;

}
