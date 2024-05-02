package br.com.projetofinal.petconnet.core.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("Address");
        tag.setName("Login");
        tag.setName("Pets");
        tag.setName("Users");
        tags.add(tag);

        return new OpenAPI()
                .info(new Info()
                        .title("PetConnect API")
                        .description("PetConnect sample application")
                        .version("v0.0.1")
                )
                .externalDocs(new ExternalDocumentation().description("").url(""))
                .tags(tags);

    }

}
