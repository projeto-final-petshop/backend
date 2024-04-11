package br.com.projetofinal.petconnet.app.users.web;

import br.com.projetofinal.petconnet.app.users.dto.request.RegisterUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.request.UpdateUserRequest;
import br.com.projetofinal.petconnet.app.users.dto.response.RegisterUserResponse;
import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe controladora para usuários. <br> Expõe endpoints para cadastro, busca e listagem de usuários.
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    /**
     * Serviço de usuário injetado por construtor.
     */
    private final UserService userService;

    /**
     * Endpoint para cadastro de um novo usuário.
     *
     * @param request
     *         Objeto contendo os dados do usuário a ser cadastrado.
     *
     * @return Retorna um {@link ResponseEntity} contendo o objeto de resposta do cadastro e código HTTP 201 (CREATED).
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * End point para listar todos os usuários.
     *
     * @return Retorna um {@link ResponseEntity} contendo uma lista de objetos de resposta dos usuários e código HTTP
     * 200 (OK).
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint para buscar um usuário por ID.
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna um {@link ResponseEntity} contendo o objeto de resposta do usuário e código HTTP 200 (OK) se
     * encontrado, caso contrário, retorna um erro apropriado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * End point para atualizar um usuário.
     * <p>
     * Observação: o retorno pode ser Status HTTP 204 No Content, que é o adequado para atualização do cadastro. Porém,
     * para retornar a mensagem informando que os dados foram atualizados, foi utilizado o Status 200 OK
     *
     * @param id
     *         Identificador do usuário.
     * @param request
     *         Objeto contendo os dados do usuário a serem atualizados.
     *
     * @return Retorna um {@link ResponseEntity} com código HTTP 200 (OK) e uma mensagem de sucesso caso a atualização
     * seja realizada com sucesso, caso contrário, retorna um erro apropriado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable(name = "id") Long id,
                                             @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * End point para buscar um usuário por username.
     *
     * @param username
     *         Email de usuário.
     *
     * @return Retorna um {@link ResponseEntity} contendo o objeto de resposta do usuário e código HTTP 200 (OK) se
     * encontrado, caso contrário, retorna um erro apropriado.
     */
    @GetMapping("/search/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable(name = "username") String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * End point para desativar um usuário.
     * <p>
     * Observação: o retorno pode ser Status HTTP 204 No Content, que é o adequado para <b>desativar</b>  o usuário.
     * Porém, para retornar a mensagem informando que os dados foram atualizados, foi utilizado o Status 200 OK. Bem
     * como poderia ser utilizado o Método DELETE ou PUT para atualizar o campo {@code active}
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna um {@link ResponseEntity} com código HTTP 200 (OK) e uma mensagem de sucesso caso a desativação
     * seja realizada com sucesso, caso contrário, retorna um erro apropriado.
     */
    @PostMapping("/{id}/disable")
    public ResponseEntity<String> disableUser(@PathVariable(name = "id") Long id) {
        userService.disableUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * End point para ativar um usuário.
     * <p>
     * Observação: o retorno pode ser Status HTTP 204 No Content, que é o adequado para <b>ativar</b> o usuário. Porém,
     * para retornar a mensagem informando que os dados foram atualizados, foi utilizado o Status 200 OK. Bem como
     * poderia ser utilizado o Método DELETE ou PUT para atualizar o campo {@code active}
     *
     * @param id
     *         Identificador do usuário.
     *
     * @return Retorna um {@link ResponseEntity} com código HTTP 200 (OK) e uma mensagem de sucesso caso a ativação seja
     * realizada com sucesso, caso contrário, retorna um erro apropriado.
     */
    @PostMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable(name = "id") Long id) {
        userService.activateUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
