package br.com.projetofinal.petconnet.pets.dto.request;

import br.com.projetofinal.petconnet.pets.entity.Pets;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EN: Detailed pet information (breed, dateOfBirth, weight, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDetailsRequest {

    private Long petDetailsId;
    private Pets pet;
    private String breed;
    private int age;
    private LocalDateTime birthday;
    private Double weight;
    private String coatColor;
    private Boolean castrated;
    private String microchip;
    private String veterinaryHistory;
    private List<String> vaccinations;
    private List<String> deworming;
    private List<String> diseases;
    private List<String> allergies;
    private List<String> medications;
    private String photo;
}
