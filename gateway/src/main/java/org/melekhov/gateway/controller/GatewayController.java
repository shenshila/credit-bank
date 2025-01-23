package org.melekhov.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.gateway.feign.FeignDealClient;
import org.melekhov.gateway.feign.FeignStatementClient;
import org.melekhov.shareddto.dto.FinishRegistrationRequestDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/gateway")
@RequiredArgsConstructor
@Slf4j
public class GatewayController {

    public final FeignDealClient dealClient;
    public final FeignStatementClient statementClient;

    @PostMapping("/statement")
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
        return statementClient.generateLoanOffers(loanStatement);
    }

    @PostMapping("statement/offer")
    @Operation(summary = "Select loan offer",
            description = "The service accepts data to save the selected credit offer to the deal service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",description = "Failed to select loan offer")
    })
    public ResponseEntity<Void> selectOffer(@RequestBody LoanOfferDto loanOffer) {
        log.info("Received loan offer request: {}", loanOffer);
        return statementClient.selectOffer(loanOffer);
    }

    @Operation(summary = "Calculate Credit", description = "Полный рассчет кредита")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Failed to get statement with id {statementId}")
    })
    @PostMapping("/deal/calculate/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable("statementId") UUID statementId,
                                                @RequestBody FinishRegistrationRequestDto finishRegistrationRequest) {
        log.info("Received credit request: {}", finishRegistrationRequest);
        return dealClient.calculateCredit(statementId, finishRegistrationRequest);
    }

    @Operation(
            summary = "Send generated documents",
            description = "Отправляет запрос в микросервис Dossier для отправки сгенерированных документов клиенту.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Документы успешно отправлены"),
                    @ApiResponse(responseCode = "404", description = "Запрос с указанным StatementId не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping("/deal/document/{statementId}/send")
    public ResponseEntity<Void> sendDocument(@PathVariable UUID statementId) {
        log.info("Send document: StatementId={}", statementId);
        return dealClient.sendDocument(statementId);
    }

    @Operation(
            summary = "Sign documents",
            description = "Отправляет запрос в микросервис Dossier для подписания документов клиентом.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Документы успешно подписаны"),
                    @ApiResponse(responseCode = "404", description = "Запрос с указанным StatementId не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping("/deal/document/{statementId}/sign")
    public ResponseEntity<Void> signDocument(@PathVariable UUID statementId) {
        log.info("Sign document: StatementId={}", statementId);
        return dealClient.signDocument(statementId);
    }

    @Operation(
            summary = "Approve documents with code",
            description = "Отправляет запрос в микросервис Dossier для подтверждения документов клиентом с использованием кода.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Документы успешно подтверждены"),
                    @ApiResponse(responseCode = "404", description = "Запрос с указанным StatementId не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping("/deal/document/{statementId}/code")
    public ResponseEntity<Void> codeDocument(@PathVariable UUID statementId) {
        log.info("Code document: StatementId={}", statementId);
        return dealClient.codeDocument(statementId);
    }


}
