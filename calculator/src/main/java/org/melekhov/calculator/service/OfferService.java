package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;

import java.util.List;

public interface OfferService {
    List<LoanOfferDto> createOffers(LoanStatementRequestDto requestDto);
}
