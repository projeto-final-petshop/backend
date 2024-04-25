package br.com.projetofinal.petconnet.app.users.password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Classe responsável para implementar na request (requisição) o CPF do usuário para que seja verificado no banco de
 * dados.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassword {

    @CPF
    private String cpf;

}
