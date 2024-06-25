package br.com.finalproject.petconnect.domain.entities.help;

import br.com.finalproject.petconnect.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pet_type")
public class PetTypeEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private PetType type;

}
