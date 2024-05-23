package br.com.project.petconnect.user.dto;

import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.security.entities.RoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@Schema(title = "UserResponse", description = "Parâmetros necessários para retornar as informações do Usuário")
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;

    private RoleResponse role;

    private List<PetResponse> pets;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // INFORMAÇÕES RETORNADAS PARA O BACKEND
    private boolean enabled;
    private List<GrantedAuthority> authorities;
    private String username;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;

}
