package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.enums.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.BadRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ConflictException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
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
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado."));
    }

    public Appointment getAppointmentByIdAndUser(Long appointmentId, User user) {
        return appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));
    }

    public void validateWeekday(LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new InvalidRequestException("Não é possível realizar agendamento aos finais de semana. Dias de atendimento: 2ª feira a 6ª feira.");
        }
    }

    public void validateAvailableTimeSlot(LocalDate appointmentDate, LocalTime appointmentTime) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);
        if (appointmentTime.isBefore(startTime) || appointmentTime.isAfter(endTime)) {
            throw new InvalidRequestException("Não é possível realizar agendamento fora do horário. Horário de atendimento: 8h às 19h");
        }

        log.info("[AppointmentServiceUtil - validateAvailableTimeSlot] Validando a disponibilidade de horário para a consulta: {}", appointmentTime);

        Optional<Appointment> consultaOptional = appointmentRepository
                .findAppointmentByAppointmentDateAndAppointmentTime(appointmentDate, appointmentTime);
        consultaOptional.ifPresent(appointment -> {
            log.error("Erro: Já existe um agendamento para a data {} e hora {}", appointmentDate, appointmentTime);
            throw new ConflictException("Já existe um agendamento para a data e hora informadas.");
        });
    }

    public void validateAppointmentRequest(AppointmentRequest request) {
        if (request.getAppointmentDate() == null) {
            log.error("Erro: Data inválida.");
            throw new BadRequestException("Campo obrigatório. Preencha o campo com uma data válida.");
        }
        validateWeekday(request.getAppointmentDate());
        validateAvailableTimeSlot(request.getAppointmentDate(), request.getAppointmentTime());
    }

    public Appointment createAppointmentFromRequest(AppointmentRequest request, User user, Pet pet) {
        Appointment appointment = AppointmentMapper.petMapper().toEntity(request);
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
