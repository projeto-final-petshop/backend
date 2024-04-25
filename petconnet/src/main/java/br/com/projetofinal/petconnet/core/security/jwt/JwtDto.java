package br.com.projetofinal.petconnet.core.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    private String token;

    private String username;

    private String password;

    private Long idUser;

    private String user;

    private String roles;

    private String type = "Bearer";
}
