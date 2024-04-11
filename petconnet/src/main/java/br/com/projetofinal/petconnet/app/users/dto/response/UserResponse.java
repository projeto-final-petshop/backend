package br.com.projetofinal.petconnet.app.users.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("user_id")
    private Long id;

    @Email
    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

//    @JsonProperty("phone_number")
//    private String phoneNumber;

    @CPF
    @JsonProperty("document_number")
    private String documentNumber;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("update_at")
    private LocalDateTime updatedAt;

}
