package br.com.project.petconnect.app.scheduling.controller;

import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingRequest;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingResponse;
import br.com.project.petconnect.app.scheduling.service.SchedulingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@AllArgsConstructor
public class SchedulingController {

    private final SchedulingService schedulingService;

    @PostMapping
    public ResponseEntity<SchedulingResponse> createAgendamento(@RequestBody @Valid SchedulingRequest request) {
        SchedulingResponse response = schedulingService.createAgendamento(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/petshop/{petShopId}")
    public ResponseEntity<List<SchedulingResponse>> getAgendamentosByPetShopId(@PathVariable Long petShopId) {
        List<SchedulingResponse> response = schedulingService.getAgendamentosByPetShopId(petShopId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
