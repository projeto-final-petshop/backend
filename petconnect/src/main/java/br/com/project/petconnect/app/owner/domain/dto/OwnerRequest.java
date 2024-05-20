package br.com.project.petconnect.app.owner.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequest {

    private String name;

    @CPF
    private String cpf;

    private String phoneNumber;

    @Email
    private String email;

    private String address;

}
