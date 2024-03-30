package br.com.projetofinal.petconnet.pets.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetSpecies {

    /**
     * PT: Cachorro <br> EN: Dog
     */
    DOG("Dog"),
    /**
     * PT: Gato <br> EN: Cat
     */
    CAT("Cat"),
    /**
     * PT: Pássaro <br> EN: Bird
     */
    BIRD("Bird"),
    /**
     * PT: Réptil <br> EN: Reptile
     */
    REPTILE("Reptile"),
    /**
     * PT: Outro <br> EN: Other
     */
    OTHER("Other");

    private final String description;

}
