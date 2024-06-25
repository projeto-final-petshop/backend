package br.com.finalproject.petconnect.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetTokenResponse {

    private Long id;
    private String token;
    private LocalDateTime expiryDate;
    private Long userId; // reference to user id

}
