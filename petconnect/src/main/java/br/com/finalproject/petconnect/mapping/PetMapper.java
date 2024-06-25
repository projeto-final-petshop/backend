package br.com.finalproject.petconnect.mapping;

import br.com.finalproject.petconnect.domain.dto.request.PetRequest;
import br.com.finalproject.petconnect.domain.dto.response.PetResponse;
import br.com.finalproject.petconnect.domain.entities.Pet;
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

    Pet toEntity(PetRequest petRequest);

    PetResponse toResponse(Pet pet);

    List<PetResponse> toResponseList(List<Pet> pets);

}
