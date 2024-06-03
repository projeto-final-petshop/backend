package br.com.finalproject.petconnect.pets.entities;

import br.com.finalproject.petconnect.user.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Representa o animal de estimação do usuário
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
@Entity
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("petId")
    private Long id;

    @Size(min = 3, max = 50, message = "O nome do pet deve ter entre {min} e {max} caracteres.")
    private String name;

    @PositiveOrZero(message = "A idade do pet deve ser um número positivo ou zero.")
    private int age;

    private String color;

    private String breed;

    private String animalType;

    @Past(message = "A data de nascimento do pet deve estar no passado.")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_pet_user"))
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime updatedAt;

}
