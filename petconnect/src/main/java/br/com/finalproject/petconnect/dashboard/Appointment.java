package br.com.finalproject.petconnect.dashboard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private Long id;
    private String serviceType;
    private LocalDateTime dateTime;
    private String client;
    private String additionalInfo;

}
