package br.com.finalproject.petconnect.pets.mapping;

import br.com.finalproject.petconnect.pets.dto.request.PetRequest;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.pets.entities.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    static PetMapper petMapper() {
        return INSTANCE;
    }

    @Mapping(target = "petType", source = "petType")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "birthdate", dateFormat = "yyyy-MM-dd")
    Pet toEntity(PetRequest petRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "petType", source = "petType")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "birthdate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "age", expression = "java(calculateAge(pet.getBirthdate()))")
    PetResponse toResponse(Pet pet);

    List<PetResponse> toResponseList(List<Pet> pets);

    default int calculateAge(LocalDate birthdate) {
        return birthdate != null ? Period.between(birthdate, LocalDate.now()).getYears() : 0;
    }

}
