package org.melekhov.dossier.kafka;

import lombok.extern.slf4j.Slf4j;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerMessage {
//    @KafkaListener(topics = "topic2",
//            groupId = "${spring.kafka.consumer.group-id}",
//            containerFactory = "kafkaListenerContainerFactory")
//    void listenerWithMessageConvertor(EmailMessageDto emailMessage) {
//        System.out.println("Received message: " + emailMessage);
////        log.info("Received message: {}", emailMessage);
//    }

    @KafkaListener(topics = "baeldung", groupId = "${spring.kafka.consumer.group-id}")
    void listener(String msg) {
        System.out.println("Received message" + msg);
    }

}
