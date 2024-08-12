package br.com.finalproject.petconnect.user.entities;

import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.roles.entities.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.*;

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
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 250,
            message = "Por favor, informe o seu nome completo. O nome deve ter entre {min} e {max} caracteres.")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
            message = "Por favor, informe um e-mail válido. Exemplo: seuemail@dominio.com.")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-+]).{8,}$",
            message = "Por favor, informe uma senha. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "Por favor, informe um CPF válido. Exemplo: 123.456.789-00.")
    @CPF(message = "Por favor, informe um CPF válido.")
    private String cpf;

    @Pattern(regexp = "^\\+?\\d{9,14}$",
            message = "Insira um número de telefone que tenha entre 9 e 14 dígitos numéricos. O sinal de + é opcional.")
    private String phoneNumber;

    @Size(min = 10, max = 250,
            message = "O endereço deve ter entre {min} e {max} caracteres.")
    private String address;

    private Boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER) // Alteração para carregamento ansioso
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "FK_user_role"),
            inverseForeignKey = @ForeignKey(name = "FK_role_user")
    )
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
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
