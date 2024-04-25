package br.com.projetofinal.petconnet.app.pets.entity;

import br.com.projetofinal.petconnet.app.users.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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
    private Long id;

    @Size(min = 3)
    @Column(nullable = false)
    private String name;

    private String breed;

    private String color;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private String animalType;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private AnimalType animalType;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false, columnDefinition = "datetime")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

}
