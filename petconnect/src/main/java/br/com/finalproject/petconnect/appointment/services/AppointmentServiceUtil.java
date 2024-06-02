package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.InvalidAppointmentDateException;
import br.com.finalproject.petconnect.exceptions.runtimes.TimeSlotConflictException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AppointmentServiceUtil {

    private final AppointmentRepository appointmentRepository;

    public void validateWeekday(LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            log.error("Tentativa de agendamento em um final de semana: {}", appointmentDate);
            throw new InvalidAppointmentDateException("invalidAppointmentDate");
        }
    }

    public void validateAvailableTimeSlot(LocalDate appointmentDate, LocalTime appointmentTime) {

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);

        if (appointmentTime.isBefore(startTime) || appointmentTime.isAfter(endTime)) {
            log.error("Horário fora do expediente permitido: {}", appointmentTime);
            throw new TimeSlotConflictException("timeSlotConflict");
        }

        log.info("Validando a disponibilidade de horário para a consulta: {}", appointmentTime);

        Optional<Appointment> consultaOptional = appointmentRepository
                .findByAppointmentDateAndAndAppointmentTime(appointmentDate, appointmentTime);
        consultaOptional.ifPresent(appointment -> {
            log.error("Conflito de horário detectado. Já existe uma consulta marcada para esta data e hora.");
            throw new TimeSlotConflictException("conflitoAgendamento");
        });

    }

}
