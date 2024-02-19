package org.rail.spring2024.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "OpenApi specification",
                contact = @Contact(
                        name = "Rail Shabayev",
                        url = "https://github.com/Rail-Shabayev",
                        email = "rail.shabayev@mail.ru"
                        ),
                description = "Storehouse test project for MediaSoft",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local environment",
                        url = "http://localhost:8080/api/product"
                )
        }
)
public class OpenApiConfig {
}

