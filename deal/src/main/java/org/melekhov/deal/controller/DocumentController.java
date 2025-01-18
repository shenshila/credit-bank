package org.melekhov.deal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.service.SenderEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final SenderEmailService senderEmailService;

    @PostMapping("/{statementId}/send")
    public ResponseEntity<Void> sendDocument(@PathVariable("statementId") UUID statementId) {
        senderEmailService.sendDocuments;
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> signDocument(@PathVariable("statementId") UUID statementId) {
        senderEmailService.creditIssued;
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> codeDocument(@PathVariable("statementId") UUID statementId) {
        senderEmailService.sendSes;
        return ResponseEntity.ok().build();
    }

}
