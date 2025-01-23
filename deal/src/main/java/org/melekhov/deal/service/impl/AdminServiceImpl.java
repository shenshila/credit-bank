package org.melekhov.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.repository.StatementRepository;
import org.melekhov.deal.service.AdminService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final StatementRepository statementRepository;

    @Override
    public Statement getStatementWithId(UUID statementId) {
        log.info("Getting statement with Id={}", statementId);
        return statementRepository.findById(statementId)
                .orElseThrow(() -> new FindException());
    }

    @Override
    public List<Statement> getAllStatements() {
        log.info("Getting list of all statements");
        return statementRepository.findAll();
    }

}
