package br.com.finalproject.petconnect.appointment.repositories;

import br.com.finalproject.petconnect.appointment.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByIdAndUserId(Long appointmentId, Long id);

}
