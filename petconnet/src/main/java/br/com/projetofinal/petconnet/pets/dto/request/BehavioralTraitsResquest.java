package br.com.projetofinal.petconnet.pets.dto.request;

import br.com.projetofinal.petconnet.pets.entity.enums.PetActivityLevelEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetSocializationEnum;
import br.com.projetofinal.petconnet.pets.entity.enums.PetTrainingEnum;
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
public class BehavioralTraitsResquest {

//    private PetRequest pet;
    private String personality;
    private PetActivityLevelEnum activityLevel;
    private PetSocializationEnum socialization;
    private PetTrainingEnum training;
    private List<String> unwantedBehaviors;

}
