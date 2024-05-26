package br.com.finalproject.petconnect.password.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    @Email
    private String email;

}
