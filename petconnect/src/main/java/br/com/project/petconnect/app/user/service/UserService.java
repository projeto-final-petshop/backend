package br.com.project.petconnect.app.user.service;

import br.com.project.petconnect.app.user.domain.dto.request.UpdatePasswordRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserUpdateRequest;
import br.com.project.petconnect.app.user.domain.dto.response.UserResponse;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.app.user.mapping.UserMapper;
import br.com.project.petconnect.app.user.repository.UserRepository;
import br.com.project.petconnect.core.exceptions.generics.ConflictException;
import br.com.project.petconnect.core.exceptions.generics.EmailNotFoundException;
import br.com.project.petconnect.core.exceptions.generics.InvalidIdException;
import br.com.project.petconnect.core.exceptions.generics.ServerErrorException;
import br.com.project.petconnect.core.exceptions.user.InvalidPasswordException;
import br.com.project.petconnect.core.exceptions.user.PasswordMismatchException;
import br.com.project.petconnect.core.exceptions.user.UsernameNotFoundException;
import br.com.project.petconnect.core.validators.CpfUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * Classe de serviço responsável pelas operações de gerenciamento de usuários.
 * <p>
 * Esta classe fornece métodos para criação, atualização, recuperação e exclusão de usuários no sistema. Ela também
 * valida dados de usuário, como e-mail e CPF, para garantir a integridade dos dados.
 * <p>
 * A classe {@link UserService} utiliza o repositório {@link UserRepository} para persistir os dados de usuário no banco
 * de dados. Ela também utiliza o codificador de senha {@link PasswordEncoder} para criptografar as senhas dos usuários
 * antes de persistir no banco de dados.
 *
 * @author juliane.maran
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    /**
     * Repositório de acesso a dados de usuários.
     */
    private final UserRepository userRepository;
    /**
     * Codificador de senha seguro.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Classe auxiliar para validação de senha
     */
    private final PasswordValidator passwordValidator;
    /**
     * Classe auxiliar para validação dos demais campos do usuário
     */
    private final UserValidator userValidator;

    /**
     * Cria um novo usuário no sistema.
     *
     * @param request
     *         Dados do usuário a ser criado.
     *
     * @return Objeto contendo os dados do usuário criado.
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF já esteja cadastrado.
     */
    @Transactional
    public UserResponse createUser(UserRequest request) {
        userValidator.validateUniqueFields(request);
        passwordValidator.validatePassword(request);
        UserEntity user = UserMapper.userMapper().toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        // adiciona máscara no cpf
        CpfUtil.setCpf(user, request.getCpf());
        userRepository.save(user);
        log.info("Usuário cadastrado com sucesso: {}", user.getId());
        return UserMapper.userMapper().toUserResponse(user);
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param id
     *         Identificador do usuário a ser atualizado.
     * @param request
     *         Dados de atualização do usuário.
     *
     * @return Objeto contendo os dados do usuário atualizado.
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF informado já esteja em uso por outro usuário (diferente do usuário sendo
     *         atualizado).
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request)
            throws InvalidPasswordException, PasswordMismatchException,
            UsernameNotFoundException, ServerErrorException, InvalidIdException {
        try {
            UserEntity user = userValidator.findUserById(id);
            userValidator.updateValidateUniqueFields(request, user);
            userValidator.updateUserFields(user, request);
            UserEntity savedUser = userRepository.save(user);
            log.info("Usuário atualizado com sucesso: {}", user.getId());
            return UserMapper.userMapper().toUserResponse(savedUser);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordException(e.getMessage());
        } catch (PasswordMismatchException e) {
            throw new PasswordMismatchException(e.getMessage());
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        }
    }

    /**
     * Atualiza a senha de um usuário existente.
     *
     * @param id
     *         Identificador do usuário.
     * @param request
     *         Dados para atualização da senha.
     *
     * @return Mensagem de sucesso após a atualização da senha.
     *
     * @throws InvalidPasswordException
     *         Caso a senha atual informada esteja incorreta.
     * @throws PasswordMismatchException
     *         Caso a nova senha e a confirmação de nova senha não sejam iguais.
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @Transactional
    public String updatePassword(Long id, UpdatePasswordRequest request)
            throws InvalidPasswordException, PasswordMismatchException,
            UsernameNotFoundException, ServerErrorException, InvalidIdException,
            MethodArgumentTypeMismatchException {
        try {
            UserEntity user = userValidator.findUserById(id);
            passwordValidator.validatePasswordUpdate(request, user);
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            log.info("Senha atualizada com sucesso!");
            return "Senha Atualizada com Sucesso!";
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordException(e.getMessage());
        } catch (PasswordMismatchException e) {
            throw new PasswordMismatchException(e.getMessage());
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        } catch (MethodArgumentTypeMismatchException e) {
            throw new InvalidIdException();
        }

    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return UserMapper.userMapper().toUserResponseList(users);
    }

    /**
     * Exclui um usuário do sistema.
     *
     * @param id
     *         Identificador do usuário a ser excluído.
     *
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @Transactional
    public void deleteUser(Long id) throws UsernameNotFoundException, InvalidIdException {
        try {
            UserEntity user = userValidator.findUserById(id);
            log.info("Usuário excluido com sucesso: {}", user.getId());
            userRepository.delete(user);
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Recupera um usuário específico por identificador.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Objeto contendo os dados do usuário.
     *
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) throws UsernameNotFoundException, InvalidIdException {
        try {
            UserEntity user = userValidator.findUserById(id);
            log.info("Usuário com ID {}, encontrado!", user.getId());
            return UserMapper.userMapper().toUserResponse(user);
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    public UserEntity getUser(String email) throws EmailNotFoundException, InvalidIdException {
        try {
            return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }


}
