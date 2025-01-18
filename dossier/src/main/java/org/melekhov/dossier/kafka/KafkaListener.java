package org.melekhov.dossier.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(
            topics = {"FINAL_REGISTRATION",
                    "CREATE_DOCUMENTS",
                    "SEND_DOCUMENTS",
                    "SEND_SES",
                    "CREDIT_ISSUED"},
            groupId = "credit_doc_group")
    public void listenerMessage() {
        log.info("Message from Deal: {}");

    }

}
