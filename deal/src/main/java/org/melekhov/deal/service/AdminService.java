package org.melekhov.deal.service;

import org.melekhov.deal.model.Statement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AdminService {


    Statement getStatementWithId(UUID statementId);

    List<Statement> getAllStatements();

}
