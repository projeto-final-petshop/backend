package br.com.projetofinal.petconnet.pets.entity;

import br.com.projetofinal.petconnet.pets.entity.enums.PetActivityLevelEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSocializationEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetTrainingEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * EN: Behavioral characteristics (personality, activityLevel, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BehavioralTraits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long behavioralTraitsId;

    @OneToOne
    private Pets pet;

    private String personality;

    @Enumerated(EnumType.STRING)
    private PetActivityLevelEnum activityLevel;

    @Enumerated(EnumType.STRING)
    private PetSocializationEnum socialization;

    @Enumerated(EnumType.STRING)
    private PetTrainingEnum training;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
    private List<String> unwantedBehaviors;

}
