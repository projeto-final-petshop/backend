package br.com.projetofinal.petconnet.pets.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * EN: Core pet information (id, name, species, etc.)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String name;

//    @Enumerated(EnumType.STRING)
//    private PetSexEnum sex;
//
//    @Enumerated(EnumType.STRING)
//    private PetSizeEnum size;
//
//    @Enumerated(EnumType.STRING)
//    private PetSpeciesEnum species;
//
//    @Enumerated(EnumType.STRING)
//    private PetTrainingEnum training;

//    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
//    private PetDetails details;
//
//    @ManyToOne
//    private Owner owner;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
