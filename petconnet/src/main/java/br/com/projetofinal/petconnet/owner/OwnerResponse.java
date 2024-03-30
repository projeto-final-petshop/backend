package br.com.projetofinal.petconnet.pets.dto.respose;

import br.com.projetofinal.petconnet.address.dto.AddressRequest;
import br.com.projetofinal.petconnet.pets.dto.request.PetRequest;
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
public class OwnerResponse {

    private Long ownerId;
    private String name;
    private AddressRequest address;
    private String phoneNumber;
    private String email;
    private List<PetRequest> pets;

}
