package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.AppointmentStatus;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.AppointmentNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.PetNotFoundException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.pets.repositories.PetRepository;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.APPOINTMENT_NOT_FOUND;
import static br.com.finalproject.petconnect.exceptions.dto.ErrorMessagesUtil.PET_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentService {

    private final AuthUtils authUtils;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public AppointmentResponse scheduleAppointment(AppointmentRequest request,
                                                   String authorizationHeader) {

        log.info("Iniciando agendamento para pet ID: {}", request.getPetId());
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);

        Pet pet = petRepository.findByIdAndUserId(request.getPetId(), user.getId())
                .orElseThrow(() -> {
                    log.error("Pet não encontrado ou não pertence ao usuário. Pet ID: {}", request.getPetId());
                    return new PetNotFoundException(PET_NOT_FOUND);
                });

        Appointment appointment = AppointmentMapper.petMapper().toEntity(request);
        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        log.info("Salvando agendamento para o pet ID: {} e usuário: {}", pet.getId(), user.getUsername());
        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento salvo com sucesso. Agendamento ID: {}", savedAppointment.getId());

        return AppointmentMapper.petMapper().toResponse(savedAppointment);

    }

    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request,
                                                 String authorizationHeader) {
        log.info("Iniciando atualização do agendamento ID: {}", appointmentId);

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        log.info("Usuário autenticado: {}", user.getUsername());

        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> {
                    log.error("Agendamento não encontrado ou não pertence ao usuário. Agendamento ID: {}", appointmentId);
                    return new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND);
                });

        Pet pet = petRepository.findByIdAndUserId(request.getPetId(), user.getId())
                .orElseThrow(() -> {
                    log.error("Pet não encontrado ou não pertence ao usuário. Pet ID: {}", request.getPetId());
                    return new PetNotFoundException(PET_NOT_FOUND);
                });

        appointment.setPet(pet);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setServiceType(request.getServiceType());
        appointment.setPetType(request.getPetType());

        log.info("Atualizando agendamento ID: {}", appointmentId);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", updatedAppointment.getId());

        return AppointmentMapper.petMapper().toResponse(updatedAppointment);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, String authorizationHeader) {
        log.info("Iniciando cancelamento do agendamento ID: {}", appointmentId);

        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        log.info("Usuário autenticado: {}", user.getUsername());

        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> {
                    log.error("Agendamento não encontrado ou não pertence ao usuário. Agendamento ID: {}", appointmentId);
                    return new IllegalArgumentException("Agendamento não encontrado ou não pertence ao usuário");
                });

        appointment.setStatus(AppointmentStatus.CANCELLED);
        log.info("Cancelando agendamento ID: {}", appointmentId);
        appointmentRepository.save(appointment);
        log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointmentsByUser(String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        List<Appointment> appointments = appointmentRepository.findAllByUserId(user.getId());
        return AppointmentMapper.petMapper().toResponseList(appointments);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPet(Long petId, String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Pet pet = petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new PetNotFoundException(PET_NOT_FOUND));
        List<Appointment> appointments = appointmentRepository.findAllByPetId(pet.getId());
        return AppointmentMapper.petMapper().toResponseList(appointments);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId, String authorizationHeader) {
        User user = authUtils.getUserFromAuthorizationHeader(authorizationHeader);
        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, user.getId())
                .orElseThrow(() -> new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND));
        return AppointmentMapper.petMapper().toResponse(appointment);
    }

}
