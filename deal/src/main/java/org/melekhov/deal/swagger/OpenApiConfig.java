package org.melekhov.deal.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Deal Service",
                description = "Create loan offers, select offer and complete loan credit",
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
