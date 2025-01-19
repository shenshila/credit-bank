package org.melekhov.shareddto;

import org.melekhov.shareddto.dto.EmailMessageDto;
import org.melekhov.shareddto.enums.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class SharedDtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedDtoApplication.class, args);
    }

}
