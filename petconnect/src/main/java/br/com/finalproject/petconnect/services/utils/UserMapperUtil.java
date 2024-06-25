package br.com.finalproject.petconnect.services.utils;

import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.dto.response.RoleResponse;
import br.com.finalproject.petconnect.domain.dto.response.UserResponse;
import br.com.finalproject.petconnect.domain.entities.Role;
import br.com.finalproject.petconnect.domain.entities.User;
import br.com.finalproject.petconnect.domain.enums.RoleType;
import br.com.finalproject.petconnect.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapperUtil {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserRequest userRequest) {

        if (userRequest == null) {
            return null;
        }

        Role role = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("RoleType.USER não encontrado"));

        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .cpf(userRequest.getCpf())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .active(true)
                .role(role)
                .build();
    }

    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .active(user.getActive())
                .address(user.getAddress())
                .cpf(user.getCpf())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(mapRoleToRoleResponse(user.getRole()))
                .build();
    }

    private RoleResponse mapRoleToRoleResponse(Role role) {
        if (role == null) {
            return null;
        }
        return RoleResponse.builder()
                .id(role.getId())
                .name(RoleType.valueOf(role.getName().toString()))
                .description(role.getDescription())
                .build();
    }

}
