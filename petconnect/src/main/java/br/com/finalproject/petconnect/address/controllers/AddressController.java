package br.com.finalproject.petconnect.address.controllers;

import br.com.finalproject.petconnect.address.domain.dto.AddressRequest;
import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import br.com.finalproject.petconnect.address.services.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = addressService.createAddress(addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> listAllAddresses() {
        List<AddressResponse> addressResponses = addressService.listAllAddresses();
        return ResponseEntity.ok(addressResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id,
                                                         @Valid @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = addressService.updateAddress(id, addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

}
