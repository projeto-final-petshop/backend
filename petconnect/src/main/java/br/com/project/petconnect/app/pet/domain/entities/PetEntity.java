package br.com.project.petconnect.app.pet.domain.entities;

import br.com.project.petconnect.app.pet.domain.enums.Sex;
import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String species;

    private String breed;

    private Sex sex;

    @PositiveOrZero
    private Integer age;

    private String size; // porte

    private Boolean neutered; // cadastrado

    private Boolean vaccination;

    @Lob
    private byte[] photo;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petshop_id")
    private PetShopEntity petShop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    private OwnerEntity owner;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

}
