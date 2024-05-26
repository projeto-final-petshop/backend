package br.com.finalproject.petconnect.password.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
