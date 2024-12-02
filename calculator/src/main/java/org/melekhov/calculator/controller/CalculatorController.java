package org.melekhov.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(
        name = "Calculator Controller",
        description = "Контроллер для рассчета кредита"
)
public class CalculatorController {

    CreditService calculatorService;
    OfferService offerService;

    public CalculatorController(CreditService calculatorService, OfferService offerService) {
        this.calculatorService = calculatorService;
        this.offerService = offerService;
    }

    @Operation(
            summary = " Getting Offers",
            description = "Предоставление кредитных предложений")
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> offers(@Valid @RequestBody @Parameter(description = "Кредитная выписка", required = true)
                                                         LoanStatementRequestDto statementRequest) {
        ResponseEntity<List<LoanOfferDto>> response = offerService.getOffers(statementRequest);
        return response;
    }

    @Operation(
            summary = "Calculate Credit",
            description = "Полный расчет параметров кредита"
    )
    @PostMapping("calc")
    public ResponseEntity<CreditDto> calc(@Valid @RequestBody @Parameter(description = "Данные для оценки платежеспособности клиента", required = true)
                                              ScoringDataDto scoringData) {
        ResponseEntity<CreditDto> response = calculatorService.calc(scoringData);
        return response;
    }

}
