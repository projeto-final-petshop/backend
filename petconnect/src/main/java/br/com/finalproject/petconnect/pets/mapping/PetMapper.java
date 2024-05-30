package br.com.finalproject.petconnect.pets.mapping;

import br.com.finalproject.petconnect.pets.dto.PetRequest;
import br.com.finalproject.petconnect.pets.dto.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
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

    @Mapping(target = "birthdate", dateFormat = "dd/MM/yyyy")
    Pet toEntity(PetRequest petRequest);

    @Mapping(target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "birthdate", dateFormat = "dd/MM/yyyy")
    PetResponse toResponse(Pet pet);

    List<PetResponse> toResponseList(List<Pet> pets);

}
