package br.com.finalproject.petconnect.domain.entities.help;

import br.com.finalproject.petconnect.domain.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment_status")
public class AppointmentStatusEtities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private AppointmentStatus status;

}
