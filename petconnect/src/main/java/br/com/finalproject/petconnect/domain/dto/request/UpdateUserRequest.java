package br.com.finalproject.petconnect.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Size(min = 3, max = 250, message = "O nome deve ter entre 3 e 250 caracteres.")
    private String name;

    @Pattern(regexp = "^\\+?\\d{9,14}$", message = "Número de telefone inválido.")
    private String phoneNumber;

    private String address;

}
