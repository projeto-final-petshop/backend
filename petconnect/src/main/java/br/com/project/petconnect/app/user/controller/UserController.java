package br.com.project.petconnect.app.user.controller;

import br.com.project.petconnect.app.user.domain.dto.request.UpdatePasswordRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserRequest;
import br.com.project.petconnect.app.user.domain.dto.request.UserUpdateRequest;
import br.com.project.petconnect.app.user.domain.dto.response.UserResponse;
import br.com.project.petconnect.app.user.service.UserService;
import br.com.project.petconnect.core.exceptions.generics.ConflictException;
import br.com.project.petconnect.core.exceptions.generics.EmailAlreadyRegisteredException;
import br.com.project.petconnect.core.exceptions.user.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe controladora REST responsável por operações de gerenciamento de usuários.
 * <p>
 * Esta classe expõe endpoints para o gerenciamento de usuários através de requisições HTTP. Ela delega a lógica de
 * negócio para a classe UserService e mapeia as respostas para o formato JSON.
 * <p>
 * {@code @RequestMapping("/users")}  - Define a URI base para todos os endpoints relacionados a usuários.
 *
 * @author juliane.maran
 */
@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Cadastra um novo usuário no sistema.
     * <p>
     * Este endpoint recebe uma requisição POST contendo os dados do usuário no corpo da requisição (request body) no
     * formato JSON. Os dados enviados devem ser válidos de acordo com as anotações {@code @Valid} e {@code @NotBlank}.
     *
     * @param request
     *         Dados do usuário a ser criado (no formato JSON).
     *
     * @return Objeto contendo os dados do usuário criado (no formato JSON) com código de status 201 (Created).
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF informado já esteja cadastrado.
     * @throws PasswordMismatchException
     *         Caso a senha e a confirmação de senha não sejam iguais.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody UserRequest request) throws
            EmailAlreadyRegisteredException, CpfAlreadyRegisteredException {
        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Erro ao criar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza os dados de um usuário existente.
     * <p>
     * Este endpoint recebe uma requisição PUT contendo os dados de atualização do usuário no corpo da requisição
     * (request body) no formato JSON. Os dados enviados devem ser válidos de acordo com as anotações {@code @Valid} e
     *
     * @param id
     *         Identificador do usuário a ser atualizado (na URI).
     * @param request
     *         Dados de atualização do usuário (no formato JSON).
     *
     * @return Objeto contendo os dados do usuário atualizado (no formato JSON) com código de status 200 (OK).
     *
     * @throws ConflictException
     *         Caso o e-mail ou CPF informado já esteja em uso por outro usuário.
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "id") Long id,
                                                   @Validated @RequestBody UserUpdateRequest request) throws
            EmailAlreadyRegisteredException, CpfAlreadyRegisteredException, UsernameNotFoundException {
        try {
            UserResponse response = userService.updateUser(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza a senha de um usuário existente.
     * <p>
     * Este endpoint recebe uma requisição PUT contendo os dados para atualização de senha no corpo da requisição
     * (request body) no formato JSON. Os dados enviados devem ser válidos de acordo com as anotações {@code @Valid} e
     *
     * @param id
     *         Identificador do usuário (na URI).
     * @param request
     *         Dados para atualização de senha (no formato JSON).
     *
     * @return Mensagem de sucesso após a atualização da senha com código de status 200 (OK).
     *
     * @throws InvalidPasswordException
     *         Caso a senha atual informada esteja incorreta.
     * @throws PasswordMismatchException
     *         Caso a nova senha e a confirmação de nova senha não sejam iguais.
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable(name = "id") Long id,
                                                 @Validated @RequestBody UpdatePasswordRequest request) throws
            InvalidPasswordException, PasswordMismatchException, UsernameNotFoundException {
        String message = userService.updatePassword(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Recupera todos os usuários cadastrados no sistema.
     * <p>
     * Este endpoint retorna uma lista contendo os dados de todos os usuários cadastrados no sistema com código de
     * status 200 (OK).
     *
     * @return Lista contendo os dados de todos os usuários (no formato JSON).
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Exclui um usuário do sistema.
     * <p>
     * Este endpoint recebe uma requisição DELETE informando o identificador do usuário na URI. Após a exclusão, o
     * endpoint retorna um código de status 204 (No Content).
     *
     * @param id
     *         Identificador do usuário a ser excluído (na URI).
     *
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") Long id) throws
            UsernameNotFoundException {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Erro ao buscar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Recupera um usuário específico por identificador.
     * <p>
     * Este endpoint recebe uma requisição GET informando o identificador do usuário na URI. Ele retorna os dados do
     * usuário encontrado com código de status 200 (OK).
     *
     * @param id
     *         Identificador do usuário (na URI).
     *
     * @return Objeto contendo os dados do usuário (no formato JSON).
     *
     * @throws UsernameNotFoundException
     *         Caso não seja encontrado usuário com o identificador informado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) throws
            UsernameNotFoundException {
        try {
            UserResponse response = userService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Erro ao buscar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
