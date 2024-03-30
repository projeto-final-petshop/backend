package br.com.projetofinal.petconnet.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Trenamento / Adestramento
 */
@Getter
@AllArgsConstructor
public enum PetTrainingEnum {

    /**
     * PT: Treinado (aprendeu comandos básicos e responde bem) <br> EN: Trained (has learned basic commands and responds
     * well)
     */
    TRAINED("Trained (has learned basic commands and responds well)"),
    /**
     * PT: Precisa de treinamento (requer treinamento para aprender comandos básicos ou resolver problemas
     * comportamentais) <br> EN: Needs Training (requires training to learn basic commands or address behavioral
     * issues)
     */
    NEEDS_TRAINING("Needs Training (requires training to learn basic commands or address behavioral issues)"),
    /**
     * PT: Não treinado (não recebeu nenhum treinamento formal) <br> EN: Untrained (hasn't received any formal
     * training)
     */
    UNTRAINED("Untrained (hasn't received any formal training)");

    private final String description;

}
