package br.com.projetofinal.petconnet.app.users.password;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe responsável por atualizar a senha do cadastro do usuário.
 * <p>
 * O usuário deve inserir a senha antiga, inserir uma nova senha e digitar novamente a senha nova
 * <p>
 * Nova senha (new password) e confirmar senha (confirm password) devem ser iguais.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassword {

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String newPassword; // nova senha

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    private String confirmPassword;

}
