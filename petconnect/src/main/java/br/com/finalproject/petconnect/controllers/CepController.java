package br.com.finalproject.petconnect.address;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequestMapping("/addresses")
@RestController
@AllArgsConstructor
//@CrossOrigin(
//        maxAge = 36000,
//        allowCredentials = "true",
//        value = {
//                "http://localhost:4200",
//                "http://localhost:9090",
//                "https://viacep.com.br/ws"
//        },
//        allowedHeaders = {"Authorization", "Content-Type"},
//        methods = {RequestMethod.POST, RequestMethod.GET})
public class CepController {

    private final CepServiceClient serviceClient;

    @GetMapping("/{cep}")
    public ResponseEntity<Address> createAdministrator(@PathVariable(name = "cep") String cep) {
        Address address = serviceClient.getAddress(cep);
        return address != null ? ResponseEntity.ok().body(address) : ResponseEntity.notFound().build();
    }

}
