package br.com.finalproject.petconnect.password.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    private String newPassword;
    private String confirmPassword;

}
