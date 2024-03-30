package br.com.projetofinal.petconnet.app.users.mapper;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface de mapeamento para conversão entre objetos de DTO (Data Transfer Object) e entidades de usuário.
 * <p>
 * Define métodos para converter objetos {@link RegisterUserRequest} em {@link Users}, {@link Users} em
 * {@link RegisterUserResponse}, {@link Users} em {@link UserResponse} e uma lista de {@link Users} em uma lista de
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
     * Converte um objeto {@link Users} em um objeto {@link RegisterUserRequest}.
     *
     * @param user
     *         Objeto {@link Users} representando o usuário.
     *
     * @return Objeto contendo os dados do usuário no formato de resposta da API
     */
    RegisterUserResponse toRegisterUserResponse(Users user);

    /**
     * Converte um objeto {@link RegisterUserResponse} em um objeto {@link Users}.
     *
     * @param registerUserRequest
     *         Objeto contendo os dados do usuário para cadastro.
     *
     * @return Objeto  {@link Users} representando a entidade de usuário.
     */
    Users toUsers(RegisterUserRequest registerUserRequest);

    /**
     * Converte um objeto  {@link Users} em um objeto {@link UserResponse}.
     *
     * @param user
     *         Objeto  {@link Users} representando o usuário.
     *
     * @return Objeto contendo os dados do usuário no formato de resposta da API.
     */
    UserResponse toUserResponse(Users user);

    /**
     * Converte uma lista de objetos  {@link Users} em uma lista de objetos {@link UserResponse}.
     *
     * @param usersList
     *         Lista de objetos  {@link Users} representando os usuários.
     *
     * @return Lista contendo os dados dos usuários no formato de resposta da API.
     */
    List<UserResponse> toUserResponseList(List<Users> usersList);

}
