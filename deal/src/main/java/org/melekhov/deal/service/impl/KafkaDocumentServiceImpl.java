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

//    private final KafkaTemplate<String, EmailMessageDto> messageKafkaTemplate;
//    private final KafkaTemplate<String, String> kafkaTemplateTest;
    private final StatementRepository statementRepository;
    private final KafkaTemplate<String, EmailMessageDto> emailMessageKafkaTemplate;

    @Override
    public void sendDocument(UUID statementId) {
        log.info("Sending message to finish-registration topic for statementId={}", statementId);
        EmailMessageDto emailMessage = createEmailMessage(statementId, Theme.FINISH_REGISTRATION);

        emailMessageKafkaTemplate.send("finish-registration", emailMessage);
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

    private EmailMessageDto createEmailMessage(UUID statementId, Theme theme) {
        log.info("Creating email message");
        Optional<Statement> statement = statementRepository.findById(statementId);

        return EmailMessageDto.builder()
                .address(statement.get().getClientId().getEmail())
                .statementId(statementId)
                .theme(theme)
                .build();
    }


    //    public void sendJsonMessage(EmailMessageDto message) {
//        log.info("Sending Json Serializer: {}", message);
//        log.info("-------------------------------");
//
//        messageKafkaTemplate.send("messages", message);
//    }

//    @Override
//    public void sendTestMessage(String message) {
//        log.info("Sending: {}", message);
//        log.info("-------------------------");
//
//        kafkaTemplateTest.send("test", message);
//    }


//    @Override
//    public void sendDocument(UUID statementId, EmailMessageDto emailMessageDto) {
//        log.info("Sending: {}", statementId);
//        log.info("-------------------------");
//
//        kafkaTemplate.send("testDto", emailMessageDto);
//    }

}
