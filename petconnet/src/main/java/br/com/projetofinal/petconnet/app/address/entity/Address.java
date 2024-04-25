package br.com.projetofinal.petconnet.app.address.entity;

import br.com.projetofinal.petconnet.app.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^\\d{8}$")
    @Column(nullable = false, length = 8)
    private String cep;

    @Size(min = 3)
    private String logradouro;

    private String complemento;

    private String bairro;

    @Size(min = 3)
    @Column(length = 50)
    private String localidade;

    @Size(min = 2, max = 2)
    @Column(length = 2)
    private String uf;

    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

}
