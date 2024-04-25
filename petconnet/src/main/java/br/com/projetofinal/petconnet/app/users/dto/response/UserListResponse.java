package br.com.projetofinal.petconnet.app.users.dto.response;

import br.com.projetofinal.petconnet.app.pets.dto.respose.PetListResponse;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {

    private Long id;

    @Size(min = 3)
    private String name;

    private List<PetListResponse> pets;

}
