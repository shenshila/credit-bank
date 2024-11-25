package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OfferService {
    ResponseEntity<List<LoanOfferDto>> getOffers(LoanStatementRequestDto request);
}
