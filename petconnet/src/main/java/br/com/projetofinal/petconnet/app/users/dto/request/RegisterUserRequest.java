package br.com.projetofinal.petconnet.app.users.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    @Email
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String password;

}
