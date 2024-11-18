package org.melekhov.calculator.service.impl;

import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.service.OfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    @Override
    public List<LoanOfferDto> createOffers(LoanStatementRequestDto requestDto) {
        return List.of();
    }
}
