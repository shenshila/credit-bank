package org.melekhov.deal.service;

import org.melekhov.shareddto.dto.FinishRegistrationRequestDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto request);
    void selectOffer(LoanOfferDto offer);
    void calculateCredit(UUID statementId, FinishRegistrationRequestDto request);
}
