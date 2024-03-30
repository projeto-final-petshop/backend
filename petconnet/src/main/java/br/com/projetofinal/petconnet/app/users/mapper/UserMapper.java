package br.com.projetofinal.petconnet.users.mapper;

import br.com.projetofinal.petconnet.users.dto.UserRequest;
import br.com.projetofinal.petconnet.users.dto.UserResponse;
import br.com.projetofinal.petconnet.users.entity.Users;
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

    Users toEntity(UserRequest userRequest);

    UserResponse toUserResponse(Users users);

    List<UserResponse> toUserListResponse(List<Users> usersList);

}
