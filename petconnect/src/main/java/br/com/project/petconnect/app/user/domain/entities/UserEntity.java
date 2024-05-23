package br.com.project.petconnect.app.user.domain.entities;

import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade JPA que representa um usuário do sistema.
 * <p>
 * &#064;Entity - Define a classe como uma entidade JPA mapeada para a tabela "users" no banco de dados.
 * <p>
 * &#064;Table(name = "users") - Especifica o nome da tabela associada a entidade.
 *
 * @author juliane.maran
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Identificador único do usuário (chave primária).
     * <p>
     * &#064;Id - Define o atributo como identificador único da entidade.
     * <p>
     * &#064;GeneratedValue(strategy = GenerationType.IDENTITY) - Indica que o valor é gerado automaticamente pelo banco
     * de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário.
     * <p>
     * &#064;Column(nullable = false) - Define a propriedade como obrigatória na tabela.
     * <p>
     * &#064;Size(min = 3, message = "O nome do usuário deve ter no mínimo {min} caracteres.") - Valida o tamanho mínimo
     * do nome (3 caracteres).
     */
    @Column(nullable = false)
    @Size(min = 3, message = "O nome do usuário deve ter no mínimo {min} caracteres.")
    private String name;

    /**
     * Endereço de e-mail do usuário (único).
     * <p>
     * &#064;Column(unique = true, nullable = false) - Define a propriedade como única e obrigatória na tabela.
     * <p>
     * &#064;Email(message = "O nome de usuário deve ser um endereço de email válido.") - Valida o formato do e-mail.
     * <p>
     * &#064;JsonProperty(access = JsonProperty.Access.WRITE_ONLY) - Indica que o campo será serializado apenas na
     * escrita (evitando expor a senha na resposta).
     */
    @Column(unique = true, nullable = false)
    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    /**
     * CPF do usuário (único).
     * <p>
     * &#064;Column(unique = true) - Define a propriedade como única na tabela.
     * <p>
     * &#064;CPF(message = "Deve ser um número de documento do tipo CPF válido.") - Valida o formato do CPF (utilizando
     * biblioteca customizada).
     */
    @Column(unique = true)
    @CPF(message = "Deve ser um número de documento do tipo CPF válido.")
    private String cpf;

    /**
     * Número de telefone do usuário no formato E.164.
     * <p>
     * &#064;Pattern(regexp = "^\\+?[1-9]\\d{12,14}<span class="math-inline">", message \= "O número de telefone deve
     * estar no formato E\.164"\) - Valida o formato do telefone.
     */
    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
            message = "O número de telefone deve estar no formato E.164")
    private String phoneNumber;

    /**
     * Senha do usuário.
     * <p>
     * &#064;Column(nullable = false) - Define a propriedade como obrigatória na tabela.
     * <p>
     * &#064;JsonProperty(access = JsonProperty.Access.WRITE_ONLY) - Indica que o campo será serializado apenas na
     * escrita (evitando expor a senha na resposta).
     * <p>
     * &#064;Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#<span
     * class="math-inline">%^&\*\(\)\-\+\]\)\.\{8,\}</span>", message = "A senha deve conter no mínimo 8 caracteres,
     * incluindo uma letra maiúscula," + "uma letra minúscula, um número e um caractere especial.") - Valida a
     * complexidade da senha.
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, " +
                    "uma letra minúscula, um número e um caractere especial.")
    private String password;

    /**
     * Papel do usuário no sistema.
     */
    private String role;

    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_pet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<PetEntity> petSet = new HashSet<>();

    /**
     * Data e hora de criação do registro do usuário.
     * <p>
     * &#064;CreationTimestamp - Define que a data e hora serão preenchidas automaticamente na criação do registro
     * (Hibernate).
     * <p>
     * &#064;JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") - Formata a data e hora na
     * serialização para
     * <p>
     * &#064;Column(updatable = false, columnDefinition = "datetime") - Define a coluna como não atualizável e
     * especifica o tipo de dado (datetime).
     */
    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    /**
     * Data e hora de atualização do registro do usuário.
     * <p>
     * &#064;UpdateTimestamp - Define que a data e hora serão preenchidas automaticamente na atualização do registro
     * (Hibernate).
     * <p>
     * &#064;JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") - Formata a data e hora na
     * serialização para JSON (Jackson).
     * <p>
     * &#064;Column(updatable = false, columnDefinition = "datetime") - Define a coluna como não atualizável e
     * especifica o tipo de dado (datetime).
     */
    @UpdateTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

}
