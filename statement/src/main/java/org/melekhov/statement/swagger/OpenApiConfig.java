package org.melekhov.statement.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
            title = "StatementService",
            description = "Pre scoring statement, create loan offers and select offer",
            version = "1.0.0",
            contact = @Contact(
                    name = "Melekhov Andrey",
                    email = "cool.melekhov@gmail.com",
                    url = "https://github.com/shenshila"
            )
    )
)
public interface OpenApiConfig {
}
