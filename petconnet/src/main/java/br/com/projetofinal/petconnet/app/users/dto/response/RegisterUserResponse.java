package br.com.projetofinal.petconnet.app.users.dto.response;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    @Email
    private String username;

    private String name;

    private String phoneNumber;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
