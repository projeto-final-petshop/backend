package br.com.finalproject.petconnect.user.controllers;

import br.com.finalproject.petconnect.user.dto.request.FindUserRequest;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(
        maxAge = 36000,
        allowCredentials = "true",
        value = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    private final UserService userService;

    @Operation(summary = "Obter usuário autenticado",
            description = "Retorna o usuário autenticado atualmente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuário autenticado retornado com sucesso",
                    content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Atualizar usuário",
            description = "Atualiza as informações do usuário atualmente autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        userService.updateUser(currentPrincipalName, request);
        log.info("Usuário atualizado com sucesso: {}", currentPrincipalName);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!");
    }

    @Operation(summary = "Obter todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Obter usuários ativos",
            description = "Retorna uma lista de usuários ativos",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getActiveUsers() {
        List<UserResponse> response = userService.findActiveUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Obter usuários inativos",
            description = "Retorna uma lista de usuários inativos",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários inativos retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/inactive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getInactiveUsers() {
        List<UserResponse> response = userService.findInactiveUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar usuário por CPF",
            description = "Retorna as informações do usuário associado ao CPF fornecido",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários inativos retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        UserResponse response = userService.findUserByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar usuários",
            description = "Retorna uma lista de usuários com base nos parâmetros fornecidos",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/search/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> searchUsers(@RequestParam(required = false, name = "name") String name,
                                          @RequestParam(required = false, name = "email") String email,
                                          @RequestParam(required = false, name = "cpf") String cpf,
                                          @RequestParam(required = false, name = "active") Boolean active,
                                          @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.searchUsers(name, email, cpf, active, pageable);
    }

    @Operation(summary = "Buscar usuários",
            description = "Retorna uma lista de usuários com base nos parâmetros fornecidos",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findUser(@RequestParam @Valid FindUserRequest request) {
        User user = userService.findUser(request);
        log.info("Usuário encontrado: {}", user != null ? user : "Nenhum usuário encontrado para a pesquisa.");
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar usuários pelo nome",
            description = "Retorna uma lista de usuários cujo nome corresponde ao fornecido",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> listUsersByName(@RequestParam(name = "name") String name) {
        List<User> response = userService.listUsersByName(name);
        log.info("Usuários listados por nome ({}): {}", name, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna as informações do usuário associado ao ID fornecido",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") Long userId) {
        User response = userService.getUserById(userId);
        log.info("Usuário recuperado pelo ID ({}): {}", userId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Excluir usuário",
            description = "Exclui o usuário autenticado atualmente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        userService.deleteUser(currentPrincipalName);
        log.info("Usuário excluído com sucesso: {}", currentPrincipalName);
        return ResponseEntity.ok("Usuário excluído com sucesso!");
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar usuário por e-mail",
            description = "Retorna as informações do usuário associado ao e-mail fornecido",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable String email) {
        UserResponse response = userService.findUserByEmail(email);
        return ResponseEntity.ok(response);
    }

}
