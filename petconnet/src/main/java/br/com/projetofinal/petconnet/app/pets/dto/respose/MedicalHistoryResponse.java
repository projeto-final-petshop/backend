package br.com.projetofinal.petconnet.app.pets.dto.respose;

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
public class MedicalHistoryResponse {

    private Long medicalHistoryId;
//    private Pets pet;
    private List<String> vaccinations;
    private List<String> deworming;
    private List<String> diseases;
    private List<String> allergies;
    private List<String> medications;

}
