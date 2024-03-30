package br.com.projetofinal.petconnet.address.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @Pattern(regexp = "^\\d{8}$")
    private String cep;

    private String numero;

}
