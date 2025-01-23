package org.melekhov.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.repository.StatementRepository;
import org.melekhov.deal.service.KafkaDocumentService;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.melekhov.shareddto.enums.Theme;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDocumentServiceImpl implements KafkaDocumentService {

    private final StatementRepository statementRepository;
    private final KafkaTemplate<String, EmailMessageDto> emailMessageKafkaTemplate;

    @Override
    public void finishRegistration(UUID statementId) {
        log.info("Sending message to finish-registration topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.FINISH_REGISTRATION);

        emailMessageKafkaTemplate.send("finish-registration", emailMessage);
    }

    @Override
    public void createDocuments(UUID statementId) {
        log.info("Sending message to create-documents topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.CREATE_DOCUMENTS);

        emailMessageKafkaTemplate.send("create-documents", emailMessage);
    }

    @Override
    public void sendDocuments(UUID statementId) {
        log.info("Sending message to send-documents topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.SEND_DOCUMENTS);

        emailMessageKafkaTemplate.send("send-documents", emailMessage);
    }

    @Override
    public void sendSes(UUID statementId) {
        log.info("Sending message to send-ses topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.SEND_SES);

        emailMessageKafkaTemplate.send("send-ses", emailMessage);
    }

    @Override
    public void creditIssued(UUID statementId) {
        log.info("Sending message to credit-issued topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.CREDIT_ISSUED);

        emailMessageKafkaTemplate.send("credit-issued", emailMessage);
    }

    @Override
    public void statementDenied(UUID statementId) {
        log.info("Sending message to statement-denied topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.STATEMENT_DENIED);

        emailMessageKafkaTemplate.send("statement-denied", emailMessage);
    }

    private EmailMessageDto createEmailMessage(UUID statementId, Theme theme) {
        log.info("Creating email message");
        Optional<Statement> statement = statementRepository.findById(statementId);

        return EmailMessageDto.builder()
                .address(statement.get().getClientId().getEmail())
                .statementId(statementId)
                .theme(theme)
                .build();
    }

}
