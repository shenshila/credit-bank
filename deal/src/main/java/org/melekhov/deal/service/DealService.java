package org.melekhov.deal.service;

import org.melekhov.deal.dto.FinishRegistrationRequestDto;
import org.melekhov.deal.dto.LoanOfferDto;
import org.melekhov.deal.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto request);
    void selectOffer(LoanOfferDto offer);
    void calculateCredit(String loanId, FinishRegistrationRequestDto request);
}
