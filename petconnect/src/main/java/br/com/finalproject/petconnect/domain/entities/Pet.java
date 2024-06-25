package br.com.finalproject.petconnect.domain.entities;

import br.com.finalproject.petconnect.domain.entities.help.PetTypeEntities;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    private String name;
    private String breed;
    private String color;
    private LocalDate birthdate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PetTypeEntities petType;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
