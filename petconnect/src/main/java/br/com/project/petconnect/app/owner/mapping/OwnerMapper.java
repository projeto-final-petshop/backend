package br.com.project.petconnect.app.owner.mapping;

import br.com.project.petconnect.app.owner.domain.entities.OwnerEntity;
import br.com.project.petconnect.app.owner.domain.dto.OwnerRequest;
import br.com.project.petconnect.app.owner.domain.dto.OwnerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {

    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    static OwnerMapper ownerMapper() {
        return INSTANCE;
    }

    OwnerEntity toEntity(OwnerRequest ownerRequest);

    OwnerResponse toResponse(OwnerEntity ownerEntity);

}
