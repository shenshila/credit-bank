package org.melekhov.calculator.service;

import org.melekhov.shareddto.dto.CreditDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {
    CreditDto calculateCredit(ScoringDataDto scoringData);

    List<LoanOfferDto> createOffersList(LoanStatementRequestDto request);
}
