package br.com.project.petconnect.app.pet.mapping;

import br.com.project.petconnect.app.pet.domain.dto.PetRequest;
import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    static PetMapper petMapper() {
        return INSTANCE;
    }

    PetEntity toPetEntity(PetRequest petRequest);

    PetResponse toPetResponse(PetEntity pet);

    List<PetResponse> toPetResponseList(List<PetEntity> pet);

}
