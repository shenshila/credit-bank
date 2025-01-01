package org.melekhov.statement.Service;

import org.melekhov.statement.dto.LoanOfferDto;
import org.melekhov.statement.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatementService {
    List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request);
    void selectOffer(LoanOfferDto request);
}
