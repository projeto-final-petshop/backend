package br.com.projetofinal.petconnet.app.users.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {

    private Long id;

    @Email
    private String username;

    private String name;

    @CPF
    private String documentNumber;

    private LocalDateTime createdAt;

    private List<List<GrantedAuthority>> authorities;
}
