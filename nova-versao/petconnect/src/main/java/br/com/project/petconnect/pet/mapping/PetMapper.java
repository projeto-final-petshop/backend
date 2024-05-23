package br.com.project.petconnect.pet.mapping;

import br.com.project.petconnect.pet.entities.Pet;
import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Pet toEntity(PetRequest petRequest);

    @Mapping(source = "user.id", target = "userId")
    PetResponse toResponse(Pet pet);

    List<PetResponse> toResponseList(List<Pet> pet);

}
