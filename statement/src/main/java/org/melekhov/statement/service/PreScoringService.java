package org.melekhov.statement.service;

import org.melekhov.statement.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface PreScoringService {
    void preScoring(LoanStatementRequestDto request);
}
