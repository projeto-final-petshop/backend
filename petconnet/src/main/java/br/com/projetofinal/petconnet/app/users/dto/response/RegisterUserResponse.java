package br.com.projetofinal.petconnet.app.users.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {

    private Long id;

    @Email
    private String username;

    private String name;

//    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
//    private String phoneNumber;

    @CPF
    private String documentNumber;

    private Boolean active;

    private LocalDateTime createdAt;

}
