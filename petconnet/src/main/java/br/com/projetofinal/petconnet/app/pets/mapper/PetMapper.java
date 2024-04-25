package br.com.projetofinal.petconnet.app.pets.mapper;

import br.com.projetofinal.petconnet.app.pets.dto.request.PetRequest;
import br.com.projetofinal.petconnet.app.pets.dto.respose.PetResponse;
import br.com.projetofinal.petconnet.app.pets.entity.Pets;
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

    @Mapping(source = "user.id", target = "userId")
    PetResponse petToPetResponse(Pets pet);

    @Mapping(source = "birthdate",
            target = "birthdate",
            dateFormat = "yyyy-MM-dd")
    Pets petRequestToPet(PetRequest request);

    List<PetResponse> petListToPetResponseList(List<Pets> petList);

}
