package br.com.project.petconnect.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        var contact = new Contact();
        contact.setEmail("petshop.petconnect@gmail.com");
        contact.setName("Pet Connect");
        contact.setUrl("https://github.com/projeto-final-petshop");

        var localServer = new Server();
        localServer.setUrl("http://localhost:8888/api/v1");
        localServer.setDescription("URL local");

        var info = new Info();
        info.title("Pet Connection");
        info.contact(contact);
        info.version("1.0.0");
        info.description("Pet Connection");

        return new OpenAPI()
                .info(info)
                .servers(Collections.singletonList(localServer));
    }

}
