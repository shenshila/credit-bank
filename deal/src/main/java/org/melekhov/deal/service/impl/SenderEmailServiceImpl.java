package org.melekhov.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.dto.EmailMessageDto;
import org.melekhov.deal.repository.StatementRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SenderEmailServiceImpl {

    private final StatementRepository statementRepository;
    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    public void finishRegistration(String message, String topicName) {
        log.info("Sending: {}", message);

        kafkaTemplate.send(topicName, message);
    }
    public void sendMessage(String message, String topicName) {
        log.info("Sending: {}", message);

        kafkaTemplate.send(topicName, message);
    }



}
