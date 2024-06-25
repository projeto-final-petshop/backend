package br.com.finalproject.petconnect.services.impl;

import br.com.finalproject.petconnect.domain.dto.AppointmentRequest;
import br.com.finalproject.petconnect.domain.entities.Appointment;
import br.com.finalproject.petconnect.domain.enums.AppointmentStatus;
import br.com.finalproject.petconnect.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.AppointmentNotAvailableException;
import br.com.finalproject.petconnect.exceptions.InvalidAppointmentRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentDateException;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentTimeException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.domain.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
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

    private final AuthUtils authUtils;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    public User getUserFromAuthorizationHeader(String authorizationHeader) {
        return authUtils.getUserFromAuthorizationHeader(authorizationHeader);
    }

    public Pet getPetByIdAndUser(Long petId, User user) {
        return petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new FieldNotFoundException("Pet"));
    }

    public Appointment getAppointmentByIdAndUser(Long appointmentId, User user) {
        return appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> new FieldNotFoundException("Agendamento"));
    }

    public void validateWeekday(LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            log.error("Não é possível realizar agendamento aos finais de semana: {}", appointmentDate);
            throw new InvalidAppointmentRequestException("Não é possível realizar agendamento aos finais de semana. Dias de atendimento: 2ª feira a 6ª feira.");
        }
    }

    public void validateAvailableTimeSlot(LocalDate appointmentDate, LocalTime appointmentTime) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);
        if (appointmentTime.isBefore(startTime) || appointmentTime.isAfter(endTime)) {
            log.error("Não é possível realizar agendamento fora do horário: {}", appointmentTime);
            throw new InvalidAppointmentRequestException("Não é possível realizar agendamento fora do horário. Horário de atendimento: 8h às 19h");
        }

        log.info("Validando a disponibilidade de horário para a consulta: {}", appointmentTime);

        Optional<Appointment> consultaOptional = appointmentRepository
                .findAppointmentByAppointmentDateAndAppointmentTime(appointmentDate, appointmentTime);
        consultaOptional.ifPresent(appointment -> {
            log.error("Já existe um agendamento para a data {} e hora {}", appointmentDate, appointmentTime);
            throw new AppointmentNotAvailableException("Já existe um agendamento para a data e hora informadas.");
        });
    }

    public void validateAppointmentRequest(AppointmentRequest request) {
        if (request.getAppointmentDate() == null) {
            log.error("Data inválida.");
            throw new InvalidAppointmentRequestException("Campo obrigatório. Preencha o campo com uma data válida.");
        }

        LocalDate today = LocalDate.now();
        if (request.getAppointmentDate().isBefore(today)) {
            log.error("A data de agendamento não pode estar no passado: {}", request.getAppointmentDate());
            throw new PastAppointmentDateException();
        }

        LocalTime now = LocalTime.now();
        if (request.getAppointmentDate().isEqual(today) && request.getAppointmentTime().isBefore(now)) {
            log.error("O horário do agendamento não pode estar no passado: {}", request.getAppointmentTime());
            throw new PastAppointmentTimeException();
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
    }

    public void cancelAppointmentStatus(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

}
