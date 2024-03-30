package br.com.projetofinal.petconnet.app.pets.dto.request;

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

//    private PetRequest pet;
    private List<String> vaccinations;
    private List<String> deworming;
    private List<String> diseases;
    private List<String> allergies;
    private List<String> medications;

}
