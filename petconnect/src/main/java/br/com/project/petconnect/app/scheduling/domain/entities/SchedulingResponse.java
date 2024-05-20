package br.com.project.petconnect.app.scheduling.domain.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingResponse {

    private Long id;
    private Long petShopId;
    private Long petId;
    private LocalDateTime dataHora;
    private String servicos;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
