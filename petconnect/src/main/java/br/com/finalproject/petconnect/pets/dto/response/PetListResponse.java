package br.com.finalproject.petconnect.pets.dto.response;

import br.com.finalproject.petconnect.appointment.dto.AppointmentListResponse;
import br.com.finalproject.petconnect.pets.entities.enums.PetType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetListResponse {

    private Long id;
    private String name;
    private String color;
    private String breed;
    private PetType petType;
    private int age;
    private LocalDate birthdate;
    private List<AppointmentListResponse> appointments;

}
