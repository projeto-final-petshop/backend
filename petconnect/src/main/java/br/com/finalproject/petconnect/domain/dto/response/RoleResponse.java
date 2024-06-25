package br.com.finalproject.petconnect.domain.dto.response;

import br.com.finalproject.petconnect.domain.enums.RoleType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Long id;
    private String description;
    private RoleType name;

}