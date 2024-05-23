package br.com.project.petconnect.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "UserRequest", description = "Parâmetros necessários Cadastrar/Atualizar usuário")
public class UserRequest {

    private String name;

    @Email
    private String email;

    private String password;

    @CPF
    private String cpf;

    private String phoneNumber;

}
