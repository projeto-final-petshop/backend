package br.com.finalproject.petconnect.mapping;

import br.com.finalproject.petconnect.domain.dto.request.RoleRequest;
import br.com.finalproject.petconnect.domain.dto.response.RoleResponse;
import br.com.finalproject.petconnect.domain.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "name")
    Role toEntity(RoleRequest request);

    @Mapping(target = "name", source = "name")
    RoleResponse toRoleResponse(Role role);

}
