package org.melekhov.deal.mapper;

import org.melekhov.deal.dto.LoanStatementRequestDto;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.jsonb.Employment;
import org.melekhov.deal.model.jsonb.Passport;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client mapToClient(LoanStatementRequestDto request) {
        Client client = Client.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .passport(Passport.builder()
                        .series(request.getPassportSeries())
                        .number(request.getPassportNumber())
                        .build())
                .employment(Employment.builder().build())
                .build();

        return client;
    }

}