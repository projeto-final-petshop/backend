package br.com.finalproject.petconnect.mapping;

import br.com.finalproject.petconnect.domain.dto.request.AppointmentRequest;
import br.com.finalproject.petconnect.domain.dto.response.AppointmentResponse;
import br.com.finalproject.petconnect.domain.entities.Appointment;
import org.mapstruct.Mapper;
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

    Appointment toEntity(AppointmentRequest appointmentRequest);

    AppointmentResponse toAppointmentResponse(Appointment appointment);

    List<AppointmentResponse> toResponseList(List<Appointment> appointmentList);

}
