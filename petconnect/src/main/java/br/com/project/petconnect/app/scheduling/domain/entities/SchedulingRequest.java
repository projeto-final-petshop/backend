package br.com.project.petconnect.app.scheduling.domain.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingRequest {

    private Long petShopId;
    private Long petId;
    private LocalDateTime dataHora;
    private String servicos;

}
