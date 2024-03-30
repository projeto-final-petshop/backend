package br.com.projetofinal.petconnet.pets.dto.request;

import br.com.projetofinal.petconnet.address.dto.AddressRequest;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

/**
 * EN: Owner contact details (ownersName, address, phoneNumber, email)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequest {

    private String name;

    private AddressRequest address;

    private String phoneNumber;

    @Email
    private String email;

    private List<PetRequest> pets;

}
