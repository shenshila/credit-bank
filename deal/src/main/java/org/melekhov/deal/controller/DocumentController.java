package org.melekhov.deal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.service.KafkaDocumentService;
import org.melekhov.deal.service.impl.KafkaDocumentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/deal/document/")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final KafkaDocumentService kafkaDocumentService;

    @PostMapping("/{statementId}/send")
    public ResponseEntity<Void> sendDocument(@PathVariable UUID statementId) {
        log.info("Send document: StatementId={}", statementId);
        kafkaDocumentService.sendDocument(statementId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> signDocument(@PathVariable UUID statementId) {
        log.info("Sign document: StatementId={}", statementId);
        kafkaDocumentService.creditIssued(statementId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> codeDocument(@PathVariable UUID statementId) {
        log.info("Code document: StatementId={}", statementId);
        kafkaDocumentService.sendSes(statementId);

        return ResponseEntity.ok().build();
    }


//    @PostMapping("/message")
//    public ResponseEntity<Void> sendMessage(@RequestBody EmailMessageDto message) {
//        documentSenderService.sendJsonMessage(message);
//
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/test")
//    public ResponseEntity<Void> sendMessage(@RequestBody String message) {
//        documentSenderService.sendTestMessage(message);
//
//        return ResponseEntity.ok().build();
//    }


//    @PostMapping("/{statementId}/send")
//    public ResponseEntity<Void> sendDocument(@PathVariable UUID statementId,
//                                             @RequestBody EmailMessageDto emailMessageDto) {
//        documentSenderService.sendDocument(statementId, emailMessageDto);
//
//        return ResponseEntity.ok().build();
//    }

}
