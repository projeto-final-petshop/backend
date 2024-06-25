package br.com.finalproject.petconnect.services;

import br.com.finalproject.petconnect.domain.dto.AppointmentRequest;
import br.com.finalproject.petconnect.domain.dto.AppointmentResponse;
import br.com.finalproject.petconnect.domain.entities.Appointment;
import br.com.finalproject.petconnect.mapping.AppointmentMapper;
import br.com.finalproject.petconnect.repositories.AppointmentRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentDateException;
import br.com.finalproject.petconnect.exceptions.runtimes.PastAppointmentTimeException;
import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.domain.entities.Pet;
import br.com.finalproject.petconnect.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

            Appointment appointment = appointmentServiceUtil.createAppointmentFromRequest(request, user, pet);

            Appointment savedAppointment = appointmentRepository.save(appointment);
            log.info("Agendamento salvo com sucesso. Agendamento ID: {}", savedAppointment.getId());

            return AppointmentMapper.petMapper().toAppointmentResponse(savedAppointment);
        } catch (FieldNotFoundException e) {
            log.error("Erro ao criar agendamento: campo não encontrado. Mensagem: {}", e.getMessage(), e);
            throw e;
        } catch (InvalidRequestException | PastAppointmentDateException | PastAppointmentTimeException e) {
            log.error("Erro ao criar agendamento: dados inválidos. Mensagem: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao criar agendamento", e);
            throw e;
        }

    }

    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request,
                                                 String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            Pet pet = appointmentServiceUtil.getPetByIdAndUser(request.getPetId(), user);

            appointmentServiceUtil.updateAppointmentDetails(appointment, request, pet);

            Appointment updatedAppointment = appointmentRepository.save(appointment);
            log.info("Agendamento atualizado com sucesso. Agendamento ID: {}", updatedAppointment.getId());

            return AppointmentMapper.petMapper().toAppointmentResponse(updatedAppointment);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao atualizar agendamento: pet não encontrado. Pet ID: {}", request.getPetId(), e);
            throw new ResourceNotFoundException("Pet");
        } catch (InvalidRequestException e) {
            log.error("Erro ao atualizar agendamento: dados inválidos. Mensagem: {}", e.getMessage(), e);
            throw e;
        } catch (ServiceException e) {
            log.error("Erro interno ao atualizar agendamento", e);
            throw e;
        }
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            appointmentServiceUtil.cancelAppointmentStatus(appointment);

            appointmentRepository.save(appointment);
            log.info("Agendamento cancelado com sucesso. Agendamento ID: {}", appointmentId);
        } catch (Exception e) {
            log.error("Erro interno ao cancelar agendamento", e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointmentsByUser(String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            List<Appointment> appointments = appointmentRepository.findAllByUserId(user.getId());
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (Exception e) {
            log.error("Erro interno ao listar todos os agendamentos do usuário", e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPet(Long petId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Pet pet = appointmentServiceUtil.getPetByIdAndUser(petId, user);
            List<Appointment> appointments = appointmentRepository.findAllByPet_Id(pet.getId());
            return AppointmentMapper.petMapper().toResponseList(appointments);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao listar agendamentos: pet não encontrado. Pet ID: {}", petId, e);
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao listar agendamentos do pet ID {}", petId, e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId, String authorizationHeader) {
        try {
            User user = appointmentServiceUtil.getUserFromAuthorizationHeader(authorizationHeader);
            Appointment appointment = appointmentServiceUtil.getAppointmentByIdAndUser(appointmentId, user);
            return AppointmentMapper.petMapper().toAppointmentResponse(appointment);
        } catch (Exception e) {
            log.error("Erro interno ao obter detalhes do agendamento ID: {}", appointmentId, e);
            throw e;
        }
    }

}
