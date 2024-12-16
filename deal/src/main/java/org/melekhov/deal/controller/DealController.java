package org.melekhov.deal.controller;

import lombok.RequiredArgsConstructor;
import org.melekhov.deal.dto.FinishRegistrationRequestDto;
import org.melekhov.deal.dto.LoanOfferDto;
import org.melekhov.deal.dto.LoanStatementRequestDto;
import org.melekhov.deal.service.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deal")
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> calculateLoanOffer(@RequestBody LoanStatementRequestDto loanStatementRequest) {
        List<LoanOfferDto> offers = dealService.calculateLoanOffer(loanStatementRequest);
        return ResponseEntity.ok().body(offers);
    }

    @PostMapping("/offer/select")
    public ResponseEntity<Void> selectOffer (@RequestBody LoanOfferDto loanOffer) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable("statementId") String statementId,
                                                      @RequestBody FinishRegistrationRequestDto finishRegistrationRequest) {
        return ResponseEntity.ok().build();
    }

}
