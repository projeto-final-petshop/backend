package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
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
        try {
            return authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        } catch (Exception e) {
            log.error("Erro ao obter usuário a partir do cabeçalho de autorização", e);
            throw e;
        }
    }

    public Pet getPetByIdAndUser(Long petId, User user) {
        try {
            return petRepository.findByIdAndUserId(petId, user.getId())
                    .orElseThrow(() -> new PetNotFoundException(messageSource.getMessage("pet.not_found", null,
                            LocaleContextHolder.getLocale())));
        } catch (PetNotFoundException e) {
            log.error("Pet não encontrado. Pet ID: {}, User ID: {}", petId, user.getId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao buscar pet por ID e usuário. Pet ID: {}, User ID: {}", petId, user.getId(), e);
            throw e;
        }
    }

    public Appointment getAppointmentByIdAndUser(Long appointmentId, User user) {
        try {
            return appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                    .orElseThrow(() -> new ServiceBookingNotFoundException(
                            messageSource.getMessage("appointment.not_found", null,
                                    LocaleContextHolder.getLocale())));
        } catch (ServiceBookingNotFoundException e) {
            log.error("Agendamento não encontrado. Appointment ID: {}, User ID: {}", appointmentId, user.getId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao buscar agendamento por ID e usuário. Appointment ID: {}, User ID: {}", appointmentId, user.getId(), e);
            throw e;
        }
    }

    public void validateWeekday(LocalDate appointmentDate) {
        try {
            DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                log.error("Tentativa de agendamento em um final de semana: {}", appointmentDate);
                throw new InvalidServiceBookingException(messageSource.getMessage("weekend_booking", null,
                        LocaleContextHolder.getLocale()));
            }
        } catch (InvalidServiceBookingException e) {
            log.error("Agendamento em final de semana não permitido: {}", appointmentDate, e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao validar dia da semana para agendamento: {}", appointmentDate, e);
            throw e;
        }
    }

    public void validateAvailableTimeSlot(LocalDate appointmentDate, LocalTime appointmentTime) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);

        try {
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
        } catch (InvalidServiceBookingException e) {
            log.error("Horário de consulta inválido: {} às {}", appointmentDate, appointmentTime, e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao validar horário de consulta: {} às {}", appointmentDate, appointmentTime, e);
            throw e;
        }
    }

    public void validateAppointmentRequest(AppointmentRequest request) {
        if (request.getAppointmentDate() == null) {
            log.error("Data da consulta não pode ser nula.");
            throw new InvalidServiceBookingException(messageSource.getMessage("invalid_booking_date", null,
                    LocaleContextHolder.getLocale()));
        }
        validateWeekday(request.getAppointmentDate());
        validateAvailableTimeSlot(request.getAppointmentDate(), request.getAppointmentTime());
    }

    public Appointment createAppointmentFromRequest(AppointmentRequest request, User user, Pet pet) {
        Appointment appointment = AppointmentMapper.petMapper().toEntity(request);
        log.debug("Consulta mapeada para a entidade: {}", appointment);
        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return appointment;
    }

    public void updateAppointmentDetails(Appointment appointment, AppointmentRequest request, Pet pet) {
        appointment.setPet(pet);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setServiceType(request.getServiceType());
        appointment.setPetType(request.getPetType());
    }

    public void cancelAppointmentStatus(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

}
