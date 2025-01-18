package org.melekhov.statement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.statement.service.StatementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/statement")
@RequiredArgsConstructor
@Tag(name = "Statement Controller", description = "Контроллер для прескоринга, создания предложений и выбора одного предложения")
@Slf4j
public class StatementController {

    private final StatementService statementService;

    @PostMapping()
    @Operation(summary = "Calculate loan offers", description = "Receives LoanStatement and sends data to calculate 4 loan offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class),
                                    minItems = 4,
                                    maxItems = 4
                            )))
    })
    public ResponseEntity<List<LoanOfferDto>> generateLoanOffers(@RequestBody LoanStatementRequestDto loanStatement) {
        log.info("Received loan offers request: {}", loanStatement);
        List<LoanOfferDto> offers = statementService.generateLoanOffers(loanStatement);
        return ResponseEntity.ok().body(offers);
    }

    @PostMapping("/offer")
    @Operation(summary = "Select loan offer",
            description = "The service accepts data to save the selected credit offer to the deal service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",description = "Failed to select loan offer")
    })
    public ResponseEntity<Void> selectOffer(@RequestBody LoanOfferDto loanOffer) {
        log.info("Received loan offer request: {}", loanOffer);
        statementService.selectOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
