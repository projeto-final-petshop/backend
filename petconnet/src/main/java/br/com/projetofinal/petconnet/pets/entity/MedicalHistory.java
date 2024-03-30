package br.com.projetofinal.petconnet.pets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

/**
 * EN: Owner contact details
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    @Email
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Pets> pets;

}
