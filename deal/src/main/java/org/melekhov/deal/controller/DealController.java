package org.melekhov.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.service.DealService;
import org.melekhov.shareddto.dto.FinishRegistrationRequestDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Deal Controller", description = "Контроллер для выбора предложения и рассчета кредита")
@Slf4j
public class DealController {
    private final DealService dealService;

    @Operation(summary = "Getting Offers", description = "Запрос к CALCULATOR на предоставление предложений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class),
                        minItems = 4, maxItems = 4
                    ))
            )
    })
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> calculateLoanOffer(@RequestBody LoanStatementRequestDto loanStatementRequest) {
        log.info("Received loan offers request: {}", loanStatementRequest);
        List<LoanOfferDto> offers = dealService.calculateLoanOffer(loanStatementRequest);
        return ResponseEntity.ok().body(offers);
    }

    @Operation(summary = "Select Offer", description = "Выбор кредитного предложения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Failed to get statement")
    })
    @PostMapping("/offer/select")
    public ResponseEntity<Void> selectOffer (@RequestBody LoanOfferDto loanOffer) {
        log.info("Received loan offer request: {}", loanOffer);
        dealService.selectOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Calculate Credit", description = "Полный рассчет кредита")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Failed to get statement with id {statementId}")
    })
    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable("statementId") UUID statementId,
                                                @RequestBody FinishRegistrationRequestDto finishRegistrationRequest) {
        log.info("Received credit request: {}", finishRegistrationRequest);
        dealService.calculateCredit(statementId, finishRegistrationRequest);
        return ResponseEntity.ok().build();
    }

}
