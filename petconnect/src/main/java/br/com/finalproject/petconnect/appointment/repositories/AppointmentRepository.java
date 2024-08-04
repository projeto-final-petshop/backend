package br.com.finalproject.petconnect.appointment.repositories;

import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByIdAndUserId(Long appointmentId, Long id);

    List<Appointment> findAllByUserId(Long userId);

    List<Appointment> findAllByPet_Id(Long petId);

    Optional<Appointment> findAppointmentByAppointmentDateAndAppointmentTime(LocalDate date, LocalTime time);

    List<Appointment> findByUserId(Long userId);

    List<Appointment> findByServiceType(ServiceType type);

    List<Appointment> findByServiceTypeIn(List<ServiceType> types);

}