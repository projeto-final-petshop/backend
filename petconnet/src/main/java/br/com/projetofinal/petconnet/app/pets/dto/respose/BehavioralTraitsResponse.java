package br.com.projetofinal.petconnet.pets.dto.respose;

import br.com.projetofinal.petconnet.pets.entity.Pets;
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
public class BehavioralTraitsResponse {

    private Long behavioralTraitsId;
//    private PetResponse pet;
    private String personality;
    private PetActivityLevelEnum activityLevel;
    private PetSocializationEnum socialization;
    private PetTrainingEnum training;
    private List<String> unwantedBehaviors;

}
