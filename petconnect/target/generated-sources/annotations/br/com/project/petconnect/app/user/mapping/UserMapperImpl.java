package br.com.project.petconnect.app.user.mapping;

import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.dto.response.UserResponse;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T14:37:17-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toUserEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.name( userRequest.getName() );
        userEntity.email( userRequest.getEmail() );
        userEntity.cpf( userRequest.getCpf() );
        userEntity.password( userRequest.getPassword() );

        return userEntity.build();
    }

    @Override
    public UserResponse toUserResponse(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getName() );
        userResponse.email( user.getEmail() );
        userResponse.cpf( user.getCpf() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }

    @Override
    public List<UserResponse> toUserResponseList(List<UserEntity> user) {
        if ( user == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( user.size() );
        for ( UserEntity userEntity : user ) {
            list.add( toUserResponse( userEntity ) );
        }

        return list;
    }
}
