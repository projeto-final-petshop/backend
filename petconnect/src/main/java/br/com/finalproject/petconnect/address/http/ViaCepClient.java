package br.com.finalproject.petconnect.address.http;

import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    ResponseEntity<AddressResponse> getAddress(@PathVariable("cep") String cep);

    @GetMapping("/{state}/{city}/{neighborhood}/json")
    ResponseEntity<List<AddressResponse>> getAddressByNeighborhood(@PathVariable("state") String state,
                                                                   @PathVariable("city") String city,
                                                                   @PathVariable("neighborhood") String neighborhood);

    @GetMapping("/{state}/{city}/json")
    ResponseEntity<List<AddressResponse>> getAddressByCity(@PathVariable("state") String state,
                                                           @PathVariable("city") String city);

    @GetMapping("/{state}/json")
    ResponseEntity<List<AddressResponse>> getAddressByState(@PathVariable("state") String state);

}
