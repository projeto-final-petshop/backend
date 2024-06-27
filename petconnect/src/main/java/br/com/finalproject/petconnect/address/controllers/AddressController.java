package br.com.finalproject.petconnect.address.controllers;

import br.com.finalproject.petconnect.address.domain.dto.AddressRequest;
import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import br.com.finalproject.petconnect.address.services.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        AddressResponse addressResponse = addressService.createAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> listAllAddresses() {
        List<AddressResponse> addresses = addressService.listAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id,
                                                         @RequestBody @Valid AddressRequest addressRequest) {
        AddressResponse updatedAddress = addressService.updateAddress(id, addressRequest);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AddressResponse>> searchAddresses(@RequestParam(required = false) String state,
                                                                 @RequestParam(required = false) String city,
                                                                 @RequestParam(required = false) String neighborhood,
                                                                 @RequestParam(required = false) String zipCode) {
        List<AddressResponse> addresses = addressService.searchAddresses(state, city, neighborhood, zipCode);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/cep/{zipCode}")
    public ResponseEntity<AddressResponse> getAddressByZipCode(@PathVariable String zipCode) {
        AddressResponse addressResponse = addressService.getAddressByZipCode(zipCode);
        return ResponseEntity.ok(addressResponse);
    }

}
