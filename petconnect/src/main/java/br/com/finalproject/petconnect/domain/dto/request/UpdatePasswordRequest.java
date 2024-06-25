package br.com.finalproject.petconnect.domain.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
