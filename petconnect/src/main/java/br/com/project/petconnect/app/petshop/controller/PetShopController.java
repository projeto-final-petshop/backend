package br.com.project.petconnect.app.petshop.controller;

import br.com.project.petconnect.app.petshop.domain.dto.PetShopRequest;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopResponse;
import br.com.project.petconnect.app.petshop.service.PetShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petshops")
@RequiredArgsConstructor
public class PetShopController {

    private final PetShopService petShopService;

    @PostMapping
    public ResponseEntity<PetShopResponse> createPetShop(@RequestBody @Valid PetShopRequest request) {
        PetShopResponse response = petShopService.createPetShop(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetShopResponse> getPetShopById(@PathVariable(name = "id") Long id) {
        PetShopResponse response = petShopService.getPetShopById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PetShopResponse>> searchPetShops(
            @RequestParam(required = false, name = "businessName") String businessName,
            @RequestParam(required = false, name = "cnpj") String cnpj,
            @RequestParam(required = false, name = "email") String email) {

        if (businessName != null) {
            List<PetShopResponse> petShops = petShopService.getPetShopsByNomeFantasia(businessName);
            return new ResponseEntity<>(petShops, HttpStatus.OK);
        }

        if (cnpj != null) {
            PetShopResponse petShop = petShopService.getPetShopByCnpj(cnpj);
            return new ResponseEntity<>(List.of(petShop), HttpStatus.OK);
        }

        if (email != null) {
            PetShopResponse petShop = petShopService.getPetShopByEmail(email);
            return new ResponseEntity<>(List.of(petShop), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
