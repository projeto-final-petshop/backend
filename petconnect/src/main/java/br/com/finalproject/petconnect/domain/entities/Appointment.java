package br.com.finalproject.petconnect.domain.entities;

import br.com.finalproject.petconnect.domain.entities.help.AppointmentStatusEtities;
import br.com.finalproject.petconnect.domain.entities.help.ServiceTypeEntities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
@Entity
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ServiceTypeEntities serviceType;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AppointmentStatusEtities status;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}