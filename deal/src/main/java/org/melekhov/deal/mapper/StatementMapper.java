package org.melekhov.deal.mapper;

import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class StatementMapper {

    public Statement mapToStatement(Client client) {
        log.info("Creating new statement with client model: {}", client);
        Statement statement = Statement.builder()
                .createdOn(LocalDateTime.now())
                .clientId(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .build();

        return statement;

    }

}
