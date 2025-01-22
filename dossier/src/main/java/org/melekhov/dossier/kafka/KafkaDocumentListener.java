package org.melekhov.dossier.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.dossier.service.EmailSenderService;
import org.melekhov.dossier.service.PdfGenerator;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaDocumentListener {

    private final PdfGenerator pdfGenerator;
    private final EmailSenderService emailSenderService;

    private byte[] pdfContent;

    @KafkaListener(topics = "finish-registration",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void finishRegistrationListener(EmailMessageDto msg) {
        emailSenderService.send(msg, "Вам предварительно одбрен кредит, завершите заполнение данных");
    }

    @KafkaListener(topics = "create-documents",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void createDocumentsListener(EmailMessageDto msg) {
        pdfContent = pdfGenerator.generateStatementPdf(msg);
        log.info("Received message: {}. Topic={}", msg, msg.getTheme());
    }

    @KafkaListener(topics = "send-documents",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void sendDocumentsListener(EmailMessageDto msg) {
        emailSenderService.send(msg, "Вам предварительно одбрен кредит, завершите заполнение данных", pdfContent);
    }

    @KafkaListener(topics = "send-ses",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void sendSesListener(EmailMessageDto msg) {
        emailSenderService.send(msg, "Код подтверждения: ");
    }

    @KafkaListener(topics = "credit-issued",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void creditIssuedListener(EmailMessageDto msg) {
        emailSenderService.send(msg, "Вы успешно выдан кредит");
    }

    @KafkaListener(topics = "statement-denied",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void statementDeniedListener(EmailMessageDto msg) {
        emailSenderService.send(msg, "Вам отказано в кредите");
    }

}
