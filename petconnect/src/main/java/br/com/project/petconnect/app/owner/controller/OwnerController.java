package br.com.project.petconnect.app.owner.controller;

import br.com.project.petconnect.app.owner.domain.dto.OwnerRequest;
import br.com.project.petconnect.app.owner.domain.dto.OwnerResponse;
import br.com.project.petconnect.app.owner.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/register")
    public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerRequest request) {
        OwnerResponse response = ownerService.createOwner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
