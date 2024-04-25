package br.com.projetofinal.petconnet.app.users.entity;

import br.com.projetofinal.petconnet.app.pets.entity.Pets;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users implements UserDetails {


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

    @OneToMany(mappedBy = "user")
    private List<Pets> pet;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private LocalDateTime updatedAt;

    @Transient
    private boolean accountNonExpired;

    @Transient
    private boolean accountNonLocked;

    @Transient
    private boolean credentialsNonExpired;

    @Transient
    private boolean enabled;

    @Transient
    private List<GrantedAuthority> authorities;

    @Transient
    private String role;

}
