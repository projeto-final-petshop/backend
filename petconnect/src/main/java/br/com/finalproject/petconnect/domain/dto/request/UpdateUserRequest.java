package br.com.finalproject.petconnect.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Size(min = 3, max = 250, message = "Nome deve ter entre {min} e {max} caracteres.")
    private String name;

    @Pattern(regexp = "^\\+?\\d{9,14}$",
            message = "Insira um número de telefone que tenha entre 9 e 14 dígitos numéricos. O sinal de + é opcional.")
    private String phoneNumber;

    @Size(min = 10, max = 250, message = "Endereço deve ter entre {min} e {max} caracteres.")
    private String address;

}
