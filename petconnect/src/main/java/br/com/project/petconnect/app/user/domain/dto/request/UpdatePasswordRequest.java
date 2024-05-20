package br.com.project.petconnect.app.user.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Senha é obrigatório.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, " +
                    "uma letra minúscula, um número e um caractere especial.")
    private String currentPassword;

    @NotBlank(message = "Senha é obrigatório.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, " +
                    "uma letra minúscula, um número e um caractere especial.")
    private String newPassword;

    @NotBlank(message = "Senha é obrigatório.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, " +
                    "uma letra minúscula, um número e um caractere especial.")
    private String confirmNewPassword;

}
