package org.melekhov.statement.service;

import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatementService {
    List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request);
    void selectOffer(LoanOfferDto request);
}
