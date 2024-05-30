package br.com.finalproject.petconnect.roles.mapping;

import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import br.com.finalproject.petconnect.roles.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    static RoleMapper petMapper() {
        return INSTANCE;
    }

    @Mapping(target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    RoleResponse toRoleResponse(Role role);

}
