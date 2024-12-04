package org.melekhov.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("api/calculator")
@Tag(name = "Calculator Controller", description = "Контроллер для рассчета кредита")
@RequiredArgsConstructor
public class CalculatorController {

    private final OfferService offerService;

    @Operation(summary = " Getting Offers", description = "Предоставление кредитных предложений")
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> createOffersList(@Valid @RequestBody @Parameter(description = "Кредитная выписка", required = true)
                                                                   LoanStatementRequestDto statementRequest) {
        log.info("Received credit request: {}", statementRequest);
        return new ResponseEntity<>(offerService.createOffersList(statementRequest), HttpStatus.OK);
    }

    @Operation(summary = "Calculate Credit", description = "Полный расчет параметров кредита")
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateCredit(@Valid @RequestBody @Parameter(description = "Данные для оценки платежеспособности клиента", required = true)
                                              ScoringDataDto scoringData) {
        log.info("Received loan offer request: {}", scoringData);
        return offerService.calculateCredit(scoringData);
    }

}
