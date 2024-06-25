package br.com.finalproject.petconnect.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private String number;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
