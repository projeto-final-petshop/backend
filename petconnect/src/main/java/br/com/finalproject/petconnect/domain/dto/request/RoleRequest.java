package br.com.finalproject.petconnect.domain.dto.request;

import br.com.finalproject.petconnect.domain.entities.help.RoleTypeEntities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    private String description;
    private RoleTypeEntities name;

}
