package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.service.InvalidServiceBookingException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentService {

    private final MessageSource messageSource;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceUtil appointmentServiceUtil;

    @Transactional
    public AppointmentResponse scheduleAppointment(AppointmentRequest request,
                                                   String authorizationHeader) {

        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        validateAppointmentRequest(request);

        Pet pet = appointmentServiceUtil.getPetByIdAndUser(request.getPetId(), user);

        Appointment appointment = createAppointmentFromRequest(request, user, pet);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento salvo com sucesso. Agendamento ID: {}", savedAppointment.getId());

        return AppointmentMapper.petMapper().toResponse(savedAppointment);
    }

    private void validateAppointmentRequest(AppointmentRequest request) {
        if (request.getAppointmentDate() == null) {
            log.error("Data da consulta não pode ser nula.");
            throw new InvalidServiceBookingException(messageSource.getMessage("invalid_booking_date", null,
                    LocaleContextHolder.getLocale()));
        }
        appointmentServiceUtil.validateWeekday(request.getAppointmentDate());
        appointmentServiceUtil.validateAvailableTimeSlot(request.getAppointmentDate(), request.getAppointmentTime());
    }

    private Appointment createAppointmentFromRequest(AppointmentRequest request, User user, Pet pet) {
        Appointment appointment = AppointmentMapper.petMapper().toEntity(request);
        log.debug("Consulta mapeada para a entidade: {}", appointment);
        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return appointment;
    }


    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request,
                                                 String authorizationHeader) {
        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
        Pet pet = appointmentServiceUtil.getPetByIdAndUser(request.getPetId(), user);

        updateAppointmentDetails(appointment, request, pet);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", updatedAppointment.getId());

        return AppointmentMapper.petMapper().toResponse(updatedAppointment);
    }

    private void updateAppointmentDetails(Appointment appointment, AppointmentRequest request, Pet pet) {
        appointment.setPet(pet);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setServiceType(request.getServiceType());
        appointment.setPetType(request.getPetType());
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, String authorizationHeader) {
        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
        cancelAppointmentStatus(appointment);

        appointmentRepository.save(appointment);
        log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
    }

    private void cancelAppointmentStatus(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointmentsByUser(String authorizationHeader) {
        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        List<Appointment> appointments = appointmentRepository.findAllByUserId(user.getId());
        return AppointmentMapper.petMapper().toResponseList(appointments);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPet(Long petId, String authorizationHeader) {
        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        Pet pet = appointmentServiceUtil.getPetByIdAndUser(petId, user);
        List<Appointment> appointments = appointmentRepository.findAllByPetId(pet.getId());
        return AppointmentMapper.petMapper().toResponseList(appointments);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId, String authorizationHeader) {
        User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
        Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
        return AppointmentMapper.petMapper().toResponse(appointment);
    }

}
