package br.com.finalproject.petconnect.user.entities;

import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.roles.entities.Role;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Representa o usuário do sistema
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_cpf", columnNames = "cpf")
})
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userId")
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @Email(message = "Por favor, insira um endereço de e-mail válido.",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "A senha deve conter pelo menos 8 caracteres, " +
                    "incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    private String password;

    @Column(unique = true, nullable = false)
    @CPF(message = "Por favor, insira um CPF válido.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Pattern(regexp = "^\\+?\\d{13,14}$",
            message = "Por favor, insira um número de telefone válido no formato E.164.")
    private String phoneNumber;

    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pet> pets;

    @ManyToOne // inserir inicialização
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_user_role"))
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
