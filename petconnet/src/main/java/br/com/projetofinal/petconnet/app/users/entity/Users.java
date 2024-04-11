package br.com.projetofinal.petconnet.app.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário deve ter no mínimo 3 caracteres
     */
    @Size(min = 3)
    private String name;

    /**
     * O username é o email do usuário e deve ser um email válido.
     */
    @Email
    @Column(unique = true)
    private String username;

    /**
     * O número de documento é o CPF do usuário e deve ser um CPF válido.
     */
    @CPF
    @Column(unique = true)
    private String documentNumber;

    /**
     * A senha deve ter no mínimo 8 caracteres, conter pelo menos uma letra maiúscula, uma letra minúscula, um número e
     * um caractere especial.
     * <p>
     * Exemplos de senhas válidas: <br> - Senha123! <br> - Abc123$d <br> - Teste@123 <br> - _JoaoSilva2023
     */
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$")
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
    @Column(length = 14)
    @Pattern(regexp = "^(\\+?)([0-9]{1,14})$")
    private String phoneNumber;

    /**
     * O usuário ao se cadastrar no sistema será automaticamente incluido como ativo = true
     * <p>
     * Quando for excluir a conta é passado para inativo = false
     * <p>
     * O banco de dados deverá ficar responsável por monitorar, caso a conta não seja ativa dentro de um determinado
     * para prazo (ex.: 30 dias) os dados são excluídos automaticamente do banco de dados.
     */
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
