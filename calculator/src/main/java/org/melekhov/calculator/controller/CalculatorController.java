package org.melekhov.calculator.controller;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CalculatorService;
import org.melekhov.calculator.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/controller")
public class CalculatorController {

    CalculatorService calculatorService;
    OfferService offerService;

    public CalculatorController(CalculatorService calculatorService, OfferService offerService) {
        this.calculatorService = calculatorService;
        this.offerService = offerService;
    }

    @PostMapping("offers")
    public ResponseEntity<List<LoanOfferDto>> offers(LoanStatementRequestDto statementRequest) {
        ResponseEntity<List<LoanOfferDto>> response = offerService.getOffers(statementRequest);
        return response;
    }

//    @PostMapping("calc")
//    public ResponseEntity<CreditDto> calc(ScoringDataDto scoringData) {
//        ResponseEntity<CreditDto> response = calculatorService.calc(scoringData);
//        return response;
//    }

}
