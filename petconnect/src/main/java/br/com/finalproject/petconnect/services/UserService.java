package br.com.finalproject.petconnect.services;

import br.com.finalproject.petconnect.domain.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.domain.dto.request.UserRequest;
import br.com.finalproject.petconnect.domain.dto.response.UserResponse;

public interface UserService {

    void deactivateUser(String email);

    UserResponse registerUser(UserRequest userRequest);

    void updateUser(String email, UpdateUserRequest request);

}
