package br.com.finalproject.petconnect.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    private String email;

    private String password;

    private String name;


}
