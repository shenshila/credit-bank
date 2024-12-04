package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {
    ResponseEntity<CreditDto> calculateCredit(ScoringDataDto scoringData);

    List<LoanOfferDto> createOffersList(LoanStatementRequestDto request);
}
