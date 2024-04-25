package br.com.projetofinal.petconnet.app.users.mapper;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface de mapeamento para conversão entre objetos de DTO (Data Transfer Object) e entidades de usuário.
 * <p>
 * Define métodos para converter objetos {@link RegisterUserRequest} em {@link User}, {@link User} em
 * {@link RegisterUserResponse}, {@link User} em {@link UserResponse} e uma lista de {@link User} em uma lista de
 * {@link UserResponse}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Instância singleton do mapper.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Recupera a instância singleton do mapper.
     *
     * @return Retorna a instância singleton do {@link UserMapper}.
     */
    static UserMapper userMapper() {
        return INSTANCE;
    }

    /**
     * Converte um objeto {@link User} em um objeto {@link RegisterUserRequest}.
     *
     * @param user
     *         Objeto {@link User} representando o usuário.
     *
     * @return Objeto contendo os dados do usuário no formato de resposta da API
     */
    RegisterUserResponse toRegisterUserResponse(User user);

    /**
     * Converte um objeto {@link RegisterUserResponse} em um objeto {@link User}.
     *
     * @param registerUserRequest
     *         Objeto contendo os dados do usuário para cadastro.
     *
     * @return Objeto  {@link User} representando a entidade de usuário.
     */
    User toUsers(RegisterUserRequest registerUserRequest);

    /**
     * Converte um objeto  {@link User} em um objeto {@link UserResponse}.
     *
     * @param user
     *         Objeto  {@link User} representando o usuário.
     *
     * @return Objeto contendo os dados do usuário no formato de resposta da API.
     */
    UserResponse toUserResponse(User user);

    /**
     * Converte uma lista de objetos  {@link User} em uma lista de objetos {@link UserResponse}.
     *
     * @param userList
     *         Lista de objetos  {@link User} representando os usuários.
     *
     * @return Lista contendo os dados dos usuários no formato de resposta da API.
     */
    List<UserResponse> toUserResponseList(List<User> userList);

}
