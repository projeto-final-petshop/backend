package br.com.projetofinal.petconnet.app.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetSocializationEnum { // Não há necessidade de classe enum separada

    /**
     * PT: Amigável (confortável perto de pessoas e outros animais)
     * <p>
     * EN: Friendly (comfortable around people and other animals)
     */
    FRIENDLY("Friendly (comfortable around people and other animals)"),
    /**
     * PT: Neutro (indiferente a pessoas ou outros animais)
     * <p>
     * EN: Neutral (indifferent to people or other animals)
     */
    NEUTRAL("Neutral (indifferent to people or other animals)"),
    /**
     * PT: Tímid\o (tímido ou apreensivo perto de pessoas ou outros animais)
     * <p>
     * EN: Shy (timid or apprehensive around people or other animals)
     */
    SHY("Shy (timid or apprehensive around people or other animals)");

    private final String description;

}
