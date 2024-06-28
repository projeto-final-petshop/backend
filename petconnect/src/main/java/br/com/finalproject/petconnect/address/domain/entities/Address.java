package br.com.finalproject.petconnect.address.domain.entities;

import br.com.finalproject.petconnect.validator.cep.ValidCep;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ValidCep
    private String zipCode;

    @Column(nullable = false)
    private String street;

    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String number;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_address_user"))
//    private User user;
}
