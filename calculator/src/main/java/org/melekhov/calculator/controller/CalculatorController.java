package org.melekhov.calculator.controller;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CalculatorService;
import org.melekhov.calculator.service.OfferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("calculator")
public class CalculatorController {

    CalculatorService calculatorService;
    OfferService offerService;

    public CalculatorController(CalculatorService calculatorService, OfferService offerService) {
        this.calculatorService = calculatorService;
        this.offerService = offerService;
    }

    @PostMapping("offers")
    public List<LoanOfferDto> offers(LoanStatementRequestDto statementRequest) {
        return offerService.createOffers(statementRequest);
    }

    @PostMapping("calc")
    public CreditDto calc(ScoringDataDto scoringData) {
        return calculatorService.calc(scoringData);
    }

}
