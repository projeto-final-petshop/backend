package br.com.project.petconnect.app.petshop.mapping;

import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopRequest;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetShopMapper {

    PetShopMapper INSTANCE = Mappers.getMapper(PetShopMapper.class);

    static PetShopMapper petShopMapper() {
        return INSTANCE;
    }

    @Mapping(target = "pets", source = "pets")
    PetShopResponse toResponse(PetShopEntity petShopEntity);

    PetShopEntity toEntity(PetShopRequest petShopRequest);


}
