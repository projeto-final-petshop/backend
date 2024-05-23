package br.com.project.petconnect.app.petshop.domain.entities;

import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "petshop")
public class PetShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome Fantasia
     */
    private String businessName;

    @CNPJ
    @Column(unique = true)
    private String cnpj;

    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
            message = "O número de telefone deve estar no formato E.164")
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    private String address;

    @Column(length = 500)
    private String services;

    /**
     * Horário de Funcionamento
     */
    private String businessHours;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "petshop_pet",
            joinColumns = @JoinColumn(name = "petshop_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<PetEntity> pets = new HashSet<>();

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
