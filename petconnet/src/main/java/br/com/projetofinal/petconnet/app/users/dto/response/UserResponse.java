package br.com.projetofinal.petconnet.app.users.dto.response;

import jakarta.validation.constraints.Email;
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
public class UserResponse {

    private Long id;

    @Email
    private String username;

    private String name;

    @CPF
    private String cpf;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
