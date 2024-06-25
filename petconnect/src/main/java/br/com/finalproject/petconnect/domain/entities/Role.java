package br.com.finalproject.petconnect.domain.entities;

import br.com.finalproject.petconnect.domain.entities.help.RoleTypeEntities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private RoleTypeEntities name;

}