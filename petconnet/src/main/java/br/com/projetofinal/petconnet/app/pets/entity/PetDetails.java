package br.com.projetofinal.petconnet.app.pets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * EN: Detailed pet information (breed, dateOfBirth, weight, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class PetDetails {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petDetailsId;

//    @OneToOne
//    private Pets pet;

    private String breed;

    private int age;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthday;

    private Double weight;

    private String coatColor;

    private Boolean castrated;

    private String microchip;

    private String veterinaryHistory;

//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "pet_details_id"))
//    private List<String> vaccinations;
//
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "pet_details_id"))
//    private List<String> deworming;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "pet_details_id"))
//    private List<String> diseases;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "pet_details_id"))
//    private List<String> allergies;
//
//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(joinColumns = @JoinColumn(name = "pet_details_id"))
//    private List<String> medications;

    private String photo;

}
