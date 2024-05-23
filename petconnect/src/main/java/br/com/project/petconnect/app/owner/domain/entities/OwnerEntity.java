package br.com.project.petconnect.app.owner.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, message = "O nome do usuário deve ter no mínimo {min} caracteres.")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    @Column(unique = true)
    @CPF(message = "Deve ser um número de documento do tipo CPF válido.")
    private String cpf;

    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
            message = "O número de telefone deve estar no formato E.164")
    private String phoneNumber;

    private String address;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "owner_pet",
//            joinColumns = @JoinColumn(name = "owner_id"),
//            inverseJoinColumns = @JoinColumn(name = "pet_id"))
//    private Set<PetEntity> petSet = new HashSet<>();

    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

}
