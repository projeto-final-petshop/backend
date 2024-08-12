package br.com.finalproject.petconnect.appointment.services;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import br.com.finalproject.petconnect.appointment.entities.enums.ServiceType;
import br.com.finalproject.petconnect.appointment.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.appointment.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.BadRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ConflictException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.InternalServerException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.pets.entities.Pet;
import br.com.finalproject.petconnect.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceUtil appointmentServiceUtil;

    @Transactional
    public AppointmentResponse scheduleAppointment(AppointmentRequest request,
                                                   String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            appointmentServiceUtil.validateAppointmentRequest(request);
            Pet pet = appointmentServiceUtil.getPetByIdAndUser(request.getPetId(), user);
            appointmentServiceUtil.validateAvailableTimeSlot(request.getAppointmentDate(), request.getAppointmentTime());

            Appointment appointment = appointmentServiceUtil.createAppointmentFromRequest(request, user, pet);
            Appointment savedAppointment = appointmentRepository.save(appointment);

            log.info("[AppointmentService - scheduleAppointment] Agendamento criado com sucesso para o usuário: {}", user.getEmail());
            return AppointmentMapper.petMapper().toAppointmentResponse(savedAppointment);
        } catch (ResourceNotFoundException e) {
            log.error("[RESOURCE NOT FOUND] - Erro ao criar agendamento: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("Pet ou usuário não encontrado.");
        } catch (BadRequestException e) {
            log.error("[BAD REQUEST] - Erro ao criar agendamento: {}", e.getMessage(), e);
            throw new BadRequestException("Requisição inválida.");
        } catch (ConflictException e) {
            log.error("[CONFLICT] - Erro ao criar agendamento: {}", e.getMessage(), e);
            throw new ConflictException("Conflito de horários.");
        } catch (Exception e) {
            log.error("[INTERNAL ERROR] - Erro interno ao criar agendamento", e);
            throw new InternalServerException("Falha ao criar novo agendamento.");
        }
    }

    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request,
                                                 String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            Pet pet = appointmentServiceUtil.getPetByIdAndUser(request.getPetId(), user);
            appointmentServiceUtil.validateAvailableTimeSlot(request.getAppointmentDate(), request.getAppointmentTime());

            appointmentServiceUtil.updateAppointmentDetails(appointment, request, pet);
            Appointment updatedAppointment = appointmentRepository.save(appointment);

            log.info("[AppointmentService - updateAppointment] Agendamento atualizado com sucesso para o usuário: {}", user.getEmail());
            return AppointmentMapper.petMapper().toAppointmentResponse(updatedAppointment);
        } catch (ResourceNotFoundException e) {
            log.error("[RESOURCE NOT FOUND] - Erro ao atualizar agendamento: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("Pet, usuário ou agendamento não encontrado.");
        } catch (BadRequestException e) {
            log.error("Erro ao atualizar agendamento: {}", e.getMessage(), e);
            throw new BadRequestException("Requisição inválida.");
        } catch (ConflictException e) {
            log.error("Erro ao atualizar agendamento: {}", e.getMessage(), e);
            throw new ConflictException("Conflito de horários.");
        } catch (Exception e) {
            log.error("Erro interno ao atualizar agendamento", e);
            throw new InternalServerException("Falha ao atualizar agendamento.");
        }
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            appointmentServiceUtil.cancelAppointmentStatus(appointment);

            appointmentRepository.save(appointment);
            log.info("[AppointmentService - cancelAppointment] Agendamento cancelado com sucesso para o usuário: {}", user.getEmail());
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao cancelar agendamento: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("Agendamento não encontrado.");
        } catch (Exception e) {
            log.error("Erro interno ao cancelar agendamento", e);
            throw new InternalServerException("Falha ao cancelar agendamento.");
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointmentsByUser(String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            List<Appointment> appointments = appointmentRepository.findAllByUserId(user.getId());
            log.info("[AppointmentService - getAllAppointmentsByUser] Listagem de todos os agendamentos do usuário: {}", user.getEmail());
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (Exception e) {
            log.error("Erro interno ao listar todos os agendamentos do usuário", e);
            throw new InternalServerException("Falha ao listar agendamentos.");
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPet(Long petId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Pet pet = appointmentServiceUtil.getPetByIdAndUser(petId, user);
            List<Appointment> appointments = appointmentRepository.findAllByPet_Id(pet.getId());
            log.info("[AppointmentService - getAppointmentsByPet] Listagem de agendamentos do pet ID: {}", petId);
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao listar agendamentos: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("Pet não encontrado.");
        } catch (Exception e) {
            log.error("Erro interno ao listar agendamentos do pet ID {}", petId, e);
            throw new InternalServerException("Falha ao buscar agendamentos.");
        }
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            log.info("[AppointmentService - getAppointmentById] Detalhes do agendamento ID: {}", appointmentId);
            return AppointmentMapper.petMapper().toAppointmentResponse(appointment);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao obter detalhes do agendamento ID: {}", appointmentId, e);
            throw new ResourceNotFoundException("Agendamento não encontrado.");
        } catch (Exception e) {
            log.error("Erro interno ao obter detalhes do agendamento ID: {}", appointmentId, e);
            throw new InternalServerException("Falha ao obter detalhes do agendamento.");
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getVeterinaryAppointments(String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            List<Appointment> appointments = appointmentRepository.findByServiceType(ServiceType.VETERINARY_CONSULTATION);
            log.info("[AppointmentService - getVeterinaryAppointments] Listagem de consultas veterinárias do usuário: {}", user.getEmail());
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (Exception e) {
            log.error("Erro interno ao listar consultas veterinárias", e);
            throw new InternalServerException("Falha ao listar consultas veterinárias.");
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getGroomingAppointments(String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            List<ServiceType> groomingTypes = Arrays.asList(
                    ServiceType.GROOMING,
                    ServiceType.BATH,
                    ServiceType.BATH_AND_GROOMING);
            List<Appointment> appointments = appointmentRepository.findByServiceTypeIn(groomingTypes);
            log.info("[AppointmentService - getGroomingAppointments] Listagem de serviços de banho e tosa do usuário: {}", user.getEmail());
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (Exception e) {
            log.error("Erro interno ao listar serviços de banho e tosa", e);
            throw new InternalServerException("Falha ao listar serviços de banho e tosa.");
        }
    }

}
