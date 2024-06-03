package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.pet.PetNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.InvalidServiceBookingException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceBookingNotFoundException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AppointmentServiceUtil {

    private final AuthUtils authUtils;
    private final PetRepository petRepository;
    private final MessageSource messageSource;
    private final AppointmentRepository appointmentRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        return authUtils.getUserFromAuthorizationHeader(authorizationHeader);
    }

    public Pet getPetByIdAndUser(Long petId, User user) {
        return petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new PetNotFoundException(messageSource.getMessage("pet.not_found", null,
                        LocaleContextHolder.getLocale())));
    }

    public Appointment getAppointmentByIdAndUser(Long appointmentId, User user) {
        return appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> new ServiceBookingNotFoundException
                        (messageSource.getMessage("appointment.not_found", null,
                                LocaleContextHolder.getLocale())));
    }

    public void validateWeekday(LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            log.error("Tentativa de agendamento em um final de semana: {}", appointmentDate);
            throw new InvalidServiceBookingException(messageSource.getMessage("weekend_booking", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    public void validateAvailableTimeSlot(LocalDate appointmentDate, LocalTime appointmentTime) {

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);

        if (appointmentTime.isBefore(startTime) || appointmentTime.isAfter(endTime)) {
            log.error("Horário fora do expediente permitido: {}", appointmentTime);
            throw new InvalidServiceBookingException(messageSource.getMessage("outside_working_hours", null,
                    LocaleContextHolder.getLocale()));
        }

        log.info("Validando a disponibilidade de horário para a consulta: {}", appointmentTime);

        Optional<Appointment> consultaOptional = appointmentRepository
                .findByAppointmentDateAndAndAppointmentTime(appointmentDate, appointmentTime);
        consultaOptional.ifPresent(appointment -> {
            log.error("Conflito de horário detectado. Já existe uma consulta marcada para esta data e hora.");
            throw new InvalidServiceBookingException(messageSource.getMessage("time_slot_conflict", null,
                    LocaleContextHolder.getLocale()));
        });

    }

}
