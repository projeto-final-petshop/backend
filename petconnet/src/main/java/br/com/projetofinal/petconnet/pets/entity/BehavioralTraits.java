package br.com.projetofinal.petconnet.pets.entity;

import jakarta.persistence.*;
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
@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pets pet;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(joinColumns = @JoinColumn(name = "medical_history_id"))
    private List<String> vaccinations;

}
