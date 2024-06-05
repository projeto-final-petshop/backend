package br.com.finalproject.petconnect;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        info = @Info(title = "PetConnect", description = "Aplicação PetConnect", version = "1.1.0"),
        servers = @Server(url = "http://localhost:8888/api/v1")
)
@EnableFeignClients(basePackages = "br.com.finalproject.petconnect.address")
@SpringBootApplication
public class PetconnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetconnectApplication.class, args);
    }

}
