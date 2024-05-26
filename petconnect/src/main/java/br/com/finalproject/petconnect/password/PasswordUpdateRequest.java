package br.com.finalproject.petconnect.password;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
