package br.com.finalproject.petconnect.appointment.mapping;

import br.com.finalproject.petconnect.appointment.dto.AppointmentRequest;
import br.com.finalproject.petconnect.appointment.dto.AppointmentResponse;
import br.com.finalproject.petconnect.appointment.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    static AppointmentMapper petMapper() {
        return INSTANCE;
    }

    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "appointmentTime", dateFormat = "HH:mm")
    @Mapping(target = "appointmentDate", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentRequest appointmentRequest);

    @Mapping(target = "status", source = "status")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "appointmentTime", dateFormat = "HH:mm")
    @Mapping(target = "appointmentDate", dateFormat = "dd/MM/yyyy")
    AppointmentResponse toAppointmentResponse(Appointment appointment);

    List<AppointmentResponse> toResponseList(List<Appointment> appointmentList);

}
