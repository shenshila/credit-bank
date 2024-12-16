package org.melekhov.deal.mapper;

import org.melekhov.deal.dto.LoanStatementRequestDto;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.deal.model.enums.ChangeType;
import org.melekhov.deal.model.jsonb.StatusHistory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
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
