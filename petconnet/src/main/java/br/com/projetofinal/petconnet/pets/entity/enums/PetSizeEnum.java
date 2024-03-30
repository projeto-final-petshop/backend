package br.com.projetofinal.petconnet.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetSize {

    /**
     * PT: Muito pequeno (toy). <br> EN: Very samll (toy).
     */
    VERY_SMALL("Very samll (toy)"),
    /**
     * PT: Pequeno. <br> EN: Small
     */
    SMALL("Small"),
    /**
     * PT: Médio <br> EN: Medium
     */
    MEDIUM("Medium"),
    /**
     * PT: Grande <br> EN: Large
     */
    LARGE("Large"),
    /**
     * PT: Gigante (muito grande) <br> EN: Giant (very large)
     */
    VERY_LARGE("Very large (giant)");

    private final String description;

}
