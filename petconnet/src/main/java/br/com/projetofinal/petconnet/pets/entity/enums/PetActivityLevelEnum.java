package br.com.projetofinal.petconnet.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetActivityLevel {

    /**
     * PT: Baixa atividade - Animais de estimação com baixos níveis de energia que requerem exercício mínimo
     * <p>
     * EN: Low activity - Pets with low energy levels that require minimal exercise.
     */
    LOW("Low activity"),

    /**
     * PT: Atividade moderada - Animais de estimação com níveis de energia médios que necessitam de exercícios
     * regulares.
     * <p>
     * EN: Moderate activity - Pets with average energy levels that need regular exercise.
     */
    MODERATE("Moderate activity"),

    /**
     * PT: Alta atividade - Animais de estimação com altos níveis de energia que requerem exercícios significativos.
     * <p>
     * EN: High activity - Pets with high energy levels that require significant exercise.
     */
    HIGH("High activity");

    private final String description;

}
