package br.com.finalproject.petconnect.address.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private String zipCode; // CEP
    private String number;
    private String publicPlace; // Logradouro
    private String complement;
    private String neighborhood;
    private String city;
    private String state;

}
