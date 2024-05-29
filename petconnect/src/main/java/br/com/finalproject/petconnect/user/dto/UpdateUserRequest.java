package br.com.finalproject.petconnect.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String name;

    @CPF
    private String cpf;

    private String phoneNumber;

}
