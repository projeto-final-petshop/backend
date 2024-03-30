package br.com.projetofinal.petconnet.app.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    private String username;

    @Size(min = 3)
    private String name;

    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
    private String phoneNumber;

}
