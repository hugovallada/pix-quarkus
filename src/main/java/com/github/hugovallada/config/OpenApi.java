package com.github.hugovallada.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
        tags = {
                @Tag(name = "Curso Quarkus 3.0", description = "Projeto inicial do curso")
        },
        info = @Info(
                title = "Quarkus 3.0",
                version = "1.0.0",
                contact = @Contact(
                        name = "Hugo",
                        url = "github.com/hugovallada",
                        email = "valladahugo@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        )
)
public class OpenApi extends Application {

}
