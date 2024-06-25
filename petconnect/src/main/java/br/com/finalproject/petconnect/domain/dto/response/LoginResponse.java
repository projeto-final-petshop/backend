package br.com.finalproject.petconnect.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String tokenType;
    private long expiresAt;
    private String username;
    private String email;
    private List<String> roles;

    public LoginResponse(String token) {
        this.token = token;
    }
}
