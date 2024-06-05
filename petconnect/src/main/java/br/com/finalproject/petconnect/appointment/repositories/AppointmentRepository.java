package br.com.finalproject.petconnect.appointment.repositories;

import br.com.finalproject.petconnect.appointment.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByIdAndUserId(Long appointmentId, Long id);

    List<Appointment> findAllByUserId(Long userId);

    List<Appointment> findAllByPetId(Long petId);

    Optional<Appointment> findByAppointmentDateAndAndAppointmentTime(LocalDate date, LocalTime time);

    void deleteByPetId(Long petId);

}
