package br.com.project.petconnect.app.user.service;

import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserUpdateRequest;
import br.com.project.petconnect.app.user.domain.entities.UserEntity;
import br.com.project.petconnect.app.user.repository.UserRepository;
import br.com.project.petconnect.core.exceptions.generics.EmailAlreadyRegisteredException;
import br.com.project.petconnect.core.exceptions.generics.InvalidIdException;
import br.com.project.petconnect.core.exceptions.generics.ServerErrorException;
import br.com.project.petconnect.core.exceptions.generics.ConflictException;
import br.com.project.petconnect.core.exceptions.user.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    /**
     * Recupera a entidade de usuário por identificador.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Entidade de usuário.
     *
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    public UserEntity findUserById(Long id)
            throws UsernameNotFoundException, InvalidIdException, ServerErrorException {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (NumberFormatException e) {
            log.error("Formato de ID de usuário inválido: {}", e.getMessage());
            throw new InvalidIdException();
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Valida se o e-mail e CPF informados para criação de usuário já estão cadastrados no sistema.
     *
     * @param request
     *         Dados do usuário a ser criado.
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF já esteja cadastrado.
     */
    public void validateUniqueFields(UserRequest request)
            throws EmailAlreadyRegisteredException, CpfAlreadyRegisteredException {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email {} já cadastrado", request.getEmail());
            throw new EmailAlreadyRegisteredException();
        }
        if (userRepository.existsByCpf(request.getCpf())) {
            log.error("CPF {} já cadastrado", request.getCpf());
            throw new CpfAlreadyRegisteredException();
        }
    }

    /**
     * Atualiza os campos da entidade de usuário com os dados informados para atualização.
     *
     * @param user
     *         Entidade de usuário.
     * @param request
     *         Dados de atualização do usuário.
     */
    public void updateUserFields(UserEntity user, UserUpdateRequest request) {
        user.setName(request.getName() != null ? request.getName() : user.getName());
        user.setCpf(request.getCpf() != null ? request.getCpf() : user.getCpf());
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : user.getPhoneNumber());
    }

    /**
     * Valida se o e-mail e CPF informados para atualização de usuário já estão em uso por outro usuário (diferente do
     * usuário sendo atualizado).
     *
     * @param request
     *         Dados de atualização do usuário.
     * @param user
     *         Entidade de usuário.
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF informado já esteja em uso por outro usuário.
     */
    public void updateValidateUniqueFields(UserUpdateRequest request, UserEntity user)
            throws CpfAlreadyRegisteredException, EmailAlreadyRegisteredException {
        if (isDuplicateEmail(request.getEmail(), user.getId())) {
            log.error("Email duplicado!");
            throw new EmailAlreadyRegisteredException();
        }
        if (isDuplicateCpf(request.getCpf(), user.getId())) {
            log.error("Número de documento duplicado!");
            throw new CpfAlreadyRegisteredException();
        }
    }

    /**
     * Verifica se o e-mail informado já está cadastrado no sistema, considerando o identificador do usuário para
     * ignorar o e-mail do próprio usuário sendo editado.
     *
     * @param email
     *         E-mail a ser verificado.
     * @param id
     *         Identificador do usuário (utilizado para ignorar o e-mail do próprio usuário).
     *
     * @return True se o e-mail já estiver cadastrado, false caso contrário.
     */
    private boolean isDuplicateEmail(String email, Long id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }

    /**
     * Verifica se o CPF informado já está cadastrado no sistema, considerando o identificador do usuário para ignorar o
     * CPF do próprio usuário sendo editado.
     *
     * @param cpf
     *         CPF a ser verificado.
     * @param id
     *         Identificador do usuário (utilizado para ignorar o CPF do próprio usuário).
     *
     * @return True se o CPF já estiver cadastrado, false caso contrário.
     */
    private boolean isDuplicateCpf(String cpf, Long id) {
        return userRepository.existsByCpfAndIdNot(cpf, id);
    }

}
