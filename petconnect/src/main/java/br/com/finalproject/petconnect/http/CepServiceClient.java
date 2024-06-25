package br.com.finalproject.petconnect.http;

import br.com.finalproject.petconnect.domain.entities.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface CepServiceClient {

    @GetMapping("/{cep}/json")
    Address getAddress(@PathVariable(name = "cep") String cep);

}
