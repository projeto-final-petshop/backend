package br.com.projetofinal.petconnet.pets;

import br.com.projetofinal.petconnet.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private String name;
    private String type;
    private Integer age;
    private String raca;

    @ManyToOne
    private Users user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
