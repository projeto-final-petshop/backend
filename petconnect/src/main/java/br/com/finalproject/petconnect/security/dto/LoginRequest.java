package br.com.finalproject.petconnect.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;

    private String password;

}
