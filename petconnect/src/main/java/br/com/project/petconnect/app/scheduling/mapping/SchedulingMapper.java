package br.com.project.petconnect.app.scheduling.mapping;

import br.com.project.petconnect.app.scheduling.domain.dto.SchedulingEntity;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingRequest;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SchedulingMapper {

    SchedulingMapper INSTANCE = Mappers.getMapper(SchedulingMapper.class);

    static SchedulingMapper serviceScheulingMapper() {
        return INSTANCE;
    }

    SchedulingEntity toEntity(SchedulingRequest serviceSchedulingRequest);

    SchedulingResponse toResponse(SchedulingEntity schedulingEntity);

}
