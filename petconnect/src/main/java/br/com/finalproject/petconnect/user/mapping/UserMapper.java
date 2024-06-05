package br.com.finalproject.petconnect.user.mapping;

import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "role.id", target = "role.id")
    @Mapping(source = "role.name", target = "role.name")
    @Mapping(source = "role.description", target = "role.description")
    @Mapping(source = "role.createdAt", target = "role.createdAt")
    @Mapping(source = "role.updatedAt", target = "role.updatedAt")
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
    
}
