package org.melekhov.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.deal.model.enums.ChangeType;
import org.melekhov.deal.service.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/deal/document/")
@RequiredArgsConstructor
@Tag(name = "Document Controller", description = "Контроллер для отправки сообщений пользователю на почту")
@Slf4j
public class DocumentController {

    private final DealService dealService;

    @Operation(
            summary = "Send generated documents",
            description = "Отправляет запрос в микросервис Dossier для отправки сгенерированных документов клиенту.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Документы успешно отправлены"),
                    @ApiResponse(responseCode = "404", description = "Запрос с указанным StatementId не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping("/{statementId}/send")
    public ResponseEntity<Void> sendDocument(@PathVariable UUID statementId) {
        log.info("Send document: StatementId={}", statementId);
        dealService.sendDocumentToKafka(statementId);
        return ResponseEntity.ok().build();
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
    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> signDocument(@PathVariable UUID statementId) {
        log.info("Sign document: StatementId={}", statementId);
        dealService.sendSesToKafka(statementId);
        return ResponseEntity.ok().build();
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
    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> codeDocument(@PathVariable UUID statementId) {
        log.info("Code document: StatementId={}", statementId);
        dealService.updateStatementStatus(statementId, ApplicationStatus.DOCUMENT_SIGNED, ChangeType.MANUAL);
        dealService.sendCreditIssued(statementId);
        return ResponseEntity.ok().build();
    }

}
