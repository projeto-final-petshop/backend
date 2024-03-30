package br.com.projetofinal.petconnet.app.pets.entity;

import lombok.*;

/**
 * EN: Medical information (vaccinations, deworming, diseases, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class MedicalHistory {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalHistoryId;

//    @OneToOne
//    private Pets pet;

//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
//    private List<String> vaccinations;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
//    private List<String> deworming;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
//    private List<String> diseases;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
//    private List<String> allergies;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
//    private List<String> medications;

}
