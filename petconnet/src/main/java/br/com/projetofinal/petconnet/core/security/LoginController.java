package br.com.projetofinal.petconnet.core.security;

import br.com.projetofinal.petconnet.app.users.dto.response.UserResponse;
import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.mapper.UserMapper;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @Tag(name = "Login")
    @Operation(summary = "get User Details After Login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login efetuado com sucesso!",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = UserResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Not Found",
                    content = @Content
            )
    })
    @GetMapping("/login")
    public ResponseEntity<UserResponse> getUserDetailsAfterLogin(Authentication authentication) {

        Optional<User> user = userRepository.findByUsername(authentication.getName());

        if (user.isPresent()) {
            UserResponse userResponse = UserMapper.userMapper().toUserResponse(user.get());
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
