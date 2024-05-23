package br.com.project.petconnect.user.mapping;

import br.com.project.petconnect.user.dto.UserRequest;
import br.com.project.petconnect.user.dto.UserResponse;
import br.com.project.petconnect.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    static UserMapper userMapper() {
        return INSTANCE;
    }

    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);

    List<UserResponse> toResponseList(List<User> user);

}
