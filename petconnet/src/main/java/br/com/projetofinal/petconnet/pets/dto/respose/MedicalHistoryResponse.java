package br.com.projetofinal.petconnet.pets.dto.request;

import br.com.projetofinal.petconnet.pets.entity.Pets;
import lombok.*;

import java.util.List;

/**
 * EN: Medical information (vaccinations, deworming, diseases, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryRequest {

    private Pets pet;
    private List<String> vaccinations;
    private List<String> deworming;
    private List<String> diseases;
    private List<String> allergies;
    private List<String> medications;

}
