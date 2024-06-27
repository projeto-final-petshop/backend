package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.PasswordMismatchException;
import br.com.finalproject.petconnect.exceptions.runtimes.conflict.FieldAlreadyExistsException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.UnauthorizedException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.JWTServiceException;
import br.com.finalproject.petconnect.roles.entities.Role;
import br.com.finalproject.petconnect.roles.entities.enums.RoleType;
import br.com.finalproject.petconnect.roles.repositories.RoleRepository;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.security.dto.LoginResponse;
import br.com.finalproject.petconnect.user.dto.request.UserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.mapping.UserMapper;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

/**
 * Serviço responsável por processar operações de autenticação, como registro de usuários.
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(UserRequest input) {

        validateInput(input);

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new FieldAlreadyExistsException("Email");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            throw new FieldAlreadyExistsException("CPF");
        }
        Role role = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> {
                    log.error("Role não encontrada.");
                    return new ResourceNotFoundException("Role não encontrada.");
                });

        User user = UserMapper.INSTANCE.toUser(input);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setActive(true);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.toUserResponse(user);

    }

    private void validateInput(UserRequest input) {
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new PasswordMismatchException("Senha nova e confirmação não coincidem.");
        }
    }

    @Transactional
    public User authenticate(LoginRequest input) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
            return (User) authentication.getPrincipal();
        } catch (UnauthorizedException e) {
            log.error("[AuthenticationService] Erro de autenticação: {}", e.getMessage());
            throw new UnauthorizedException("Credenciais inválidas.", e);
        } catch (Exception e) {
            log.error("Erro ao autenticar usuário", e);
            throw new JWTServiceException("Erro interno ao autenticar usuário", e);
        }
    }

    public LoginResponse createLoginResponse(User authenticatedUser, String jwtToken) {
        var response = new LoginResponse(jwtToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtService.getExpirationTime());
        response.setUsername(authenticatedUser.getUsername());
        response.setEmail(authenticatedUser.getEmail());
        response.setRoles(authenticatedUser.getRoles().stream()
                .map(role -> role.getName().name())
                .toList());
        return response;
    }

}