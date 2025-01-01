package org.melekhov.statement.controller;

import org.melekhov.statement.Service.StatementService;
import org.melekhov.statement.dto.LoanOfferDto;
import org.melekhov.statement.dto.LoanStatementRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/statement")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping()
    public ResponseEntity<List<LoanOfferDto>> generateLoanOffers(@RequestBody LoanStatementRequestDto loanStatement) {
        List<LoanOfferDto> offers = statementService.generateLoanOffers(loanStatement);
        return ResponseEntity.ok().body(offers);
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> selectOffer(@RequestBody LoanOfferDto loanOffer) {
        statementService.selectOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
