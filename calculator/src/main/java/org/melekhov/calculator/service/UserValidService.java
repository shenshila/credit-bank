package org.melekhov.calculator.service;

import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

@Service
public interface UserValidService {
    public void checkClientSolvency(ScoringDataDto scoringDataDto);
    public void checkClientSolvency(LoanStatementRequestDto loanStatementRequestDto);
}
