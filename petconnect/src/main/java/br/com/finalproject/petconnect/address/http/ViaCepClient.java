package br.com.finalproject.petconnect.address.http;

import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    ResponseEntity<AddressResponse> getAddress(@PathVariable(name = "cep") String cep);

}
