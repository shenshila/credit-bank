package org.melekhov.dossier.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.dossier.service.EmailSenderService;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaDocumentListener {

    private final EmailSenderService emailSenderService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = {"finish-registration",
                    "create-documents",
                    "send-documents",
                    "send-ses",
                    "credit-issued",
                    "statement-denied"},
            containerFactory = "messageKafkaListenerContainerFactory")
    public void messageListener(EmailMessageDto msg) {
        emailSenderService.send(msg, String.valueOf(msg.getTheme()));
        log.info("Received message: {}. Topic={}", msg, msg.getTheme());
    }

}
