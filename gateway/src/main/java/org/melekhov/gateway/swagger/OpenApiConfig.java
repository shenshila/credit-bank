package org.melekhov.gateway.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Gateway API",
                description = "Redirected requests to other MS",
                version = "1.0.0",
                contact = @Contact(
                        name = "Melekhov Andrey",
                        email = "cool.melekhov@gmail.com",
                        url = "https://github.com/shenshila"
                )
        )
)
public class OpenApiConfig {
}
