package br.com.projetofinal.petconnet.users;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    private Long id;

    /**
     * Nome do usuário deve ter no mínimo 3 caracteres
     */
    @Size(min = 3)
    @Column(nullable = false)
    private String name;

    /**
     * O username é o email do usuário e deve ser um email válido.
     */
    @Email
    @Column(nullable = false)
    private String username;

    /**
     * A senha deve ter no mínimo 8 caracteres, conter pelo menos uma letra maiúscula, uma letra minúscula, um número e
     * um caractere especial.
     * <p>
     * Exemplos de senhas válidas: <br> - Senha123! <br> - Abc123$d <br> - Teste@123 <br> - _JoaoSilva2023
     */
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
    @Column(nullable = false)
    private String password;

    /**
     * Este regex é específico para o formato E.164.
     * <p>
     * Este regex verifica apenas a formatação do número de telefone. Não garante que o número seja válido ou que
     * pertença a um país específico.
     * <p>
     * Exemplos de números de telefone válidos: <br> - +551199998888 <br> - 551199998888 <br> - 1234567890 <br> -
     * +9876543210
     */
    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * O usuário ao se cadastrar no sistema será automaticamente incluido como ativo = true
     * <p>
     * Quando for excluir a conta é passado para inativo = false
     * <p>
     * O banco de dados deverá ficar responsável por monitorar, caso a conta não seja ativa dentro de um determinado
     * para prazo (ex.: 30 dias) os dados são excluídos automaticamente do banco de dados.
     */
    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
