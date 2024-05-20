package br.com.project.petconnect.app.petshop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetShopRequest {

    private String businessName;

    @CNPJ
    private String cnpj;

    @Pattern(regexp = "^\\+?[1-9]\\d{12,14}$",
            message = "O número de telefone deve estar no formato E.164")
    private String phoneNumber;

    @Email(message = "O nome de usuário deve ser um endereço de email válido.")
    private String email;

    private String address;

    private String services;

    private String businessHours;

}
