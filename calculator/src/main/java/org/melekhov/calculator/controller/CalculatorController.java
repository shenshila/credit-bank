package org.melekhov.calculator.controller;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CreditService;
import org.melekhov.calculator.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/controller")
public class CalculatorController {

    CreditService calculatorService;
    OfferService offerService;

    public CalculatorController(CreditService calculatorService, OfferService offerService) {
        this.calculatorService = calculatorService;
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> offers(@RequestBody LoanStatementRequestDto statementRequest) {
        ResponseEntity<List<LoanOfferDto>> response = offerService.getOffers(statementRequest);
        return response;
    }

    @PostMapping("calc")
    public ResponseEntity<CreditDto> calc(@RequestBody ScoringDataDto scoringData) {
        ResponseEntity<CreditDto> response = calculatorService.calc(scoringData);
        return response;
    }

}
