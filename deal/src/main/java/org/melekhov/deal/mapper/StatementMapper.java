package org.melekhov.deal.mapper;

import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StatementMapper {

    public Statement mapToStatement(LoanStatementRequestDto request, Client client) {

        Statement statement = Statement.builder()
                .createdOn(LocalDateTime.now())
                .clientId(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .build();

        return statement;

    }

}
