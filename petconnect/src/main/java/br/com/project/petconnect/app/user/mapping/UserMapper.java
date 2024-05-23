package br.com.project.petconnect.app.user.mapping;

import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.dto.response.UserResponse;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface de mapeamento entre objetos de requisição/resposta e entidades de usuário utilizando MapStruct.
 * <p>
 * Esta interface define métodos de conversão entre objetos DTO (Data Transfer Object) e entidades de usuário
 * ({@link UserEntity}) utilizando a biblioteca MapStruct.
 * <p>
 * A anotação
 * {@code Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy =
 * ReportingPolicy.IGNORE))}Define o modelo de componente como Spring e ignora propriedades não mapeadas no objeto de
 * destino.
 *
 * @author juliane.maran
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Instância singleton do mapeador {@link UserMapper}. criada através do método getMapper do MapStruct.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Recupera a instância singleton do mapeador {@link UserMapper}.
     *
     * @return Instância do mapeador {@link UserMapper}..
     */
    static UserMapper userMapper() {
        return INSTANCE;
    }

    /**
     * Converte um objeto {@link UserRequest} (entrada de dados) para uma entidade {@link UserEntity} (entidade JPA).
     *
     * @param userRequest
     *         Objeto contendo os dados do usuário a ser convertido (no formato JSON).
     *
     * @return Entidade {@link UserEntity} contendo os dados do usuário mapeados.
     */
    UserEntity toUserEntity(UserRequest userRequest);

    /**
     * Converte uma entidade {@link UserEntity} (entidade JPA) para um objeto {@link UserResponse} (saída de dados).
     *
     * @param user
     *         Entidade {@link UserEntity} contendo os dados do usuário a ser convertido.
     *
     * @return Objeto {@link UserResponse} contendo os dados do usuário mapeados (no formato JSON).
     */
    UserResponse toUserResponse(UserEntity user);

    /**
     * Converte uma lista de entidades {@link UserEntity} (entidade JPA) para uma lista de objetos {@link UserResponse}
     * (saída de dados).
     *
     * @param user
     *         Lista de entidades {@link UserEntity} contendo os dados dos usuários a serem convertidos.
     *
     * @return Lista de objetos {@link UserResponse} contendo os dados dos usuários mapeados (no formato JSON).
     */
    List<UserResponse> toUserResponseList(List<UserEntity> user);

}
