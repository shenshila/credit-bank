package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

@Service
public interface UserValidService {
    public void checkClientSolvency(ScoringDataDto scoringDataDto);
    public void checkClientSolvency(LoanStatementRequestDto loanStatementRequestDto);
}
