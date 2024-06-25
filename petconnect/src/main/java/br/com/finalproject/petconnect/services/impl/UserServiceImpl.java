package br.com.finalproject.petconnect.services.impl;

import br.com.finalproject.petconnect.domain.dto.request.LoginRequest;
import br.com.finalproject.petconnect.domain.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.dto.response.LoginResponse;
import br.com.finalproject.petconnect.domain.dto.response.UserResponse;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.FieldNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.UserNotRegisteredException;
import br.com.finalproject.petconnect.repositories.UserRepository;
import br.com.finalproject.petconnect.security.services.JwtTokenProvider;
import br.com.finalproject.petconnect.services.utils.UserMapperUtil;
import br.com.finalproject.petconnect.services.utils.UserValidationService;
import br.com.finalproject.petconnect.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserMapperUtil userMapper;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final UserValidationService validationService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(UserRequest request) {
        validationService.validateUserRequest(request);
        User savedUser = userRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(userDetails);

        return authUtils.buildLoginResponse(userDetails, jwtToken);

    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUserDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotRegisteredException("Usuário não encontrado."));
        return userMapper.toResponse(user);
    }

    @Transactional
    public void updateUser(String email, UpdateUserRequest updateRequest) {
        final var user = emailNotFound(email);
        validationService.validateUpdateUserRequest(updateRequest, user);
        userRepository.save(user);
    }

    @Transactional
    public void deactivateUser(String email) {
        final var user = emailNotFound(email);
        user.setActive(false);
        userRepository.save(user);
    }

    private User emailNotFound(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new FieldNotFoundException("Email" + email));
    }

}
