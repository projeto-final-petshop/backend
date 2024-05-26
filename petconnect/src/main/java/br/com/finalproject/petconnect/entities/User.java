<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/user/entities/User.java
package br.com.finalproject.petconnect.user.entities;
========
package br.com.finalproject.petconnect.entities;
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/entities/User.java

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    /**
     * Informação exclusiva do usuário
     */
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/user/entities/User.java
    /**
     * Informação exclusiva do usuário
     */
    @CPF
    @Column(unique = true, nullable = false)
    private String cpf;

    private String phoneNumber;

    private boolean active;
========
    @Column(nullable = false, columnDefinition = "tinyint not null default 0")
    private boolean enabled;
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/entities/User.java

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    /**
     * @return uma lista de funções do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * @return o endereço de email
     */
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
