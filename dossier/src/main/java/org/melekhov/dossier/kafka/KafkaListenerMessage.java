package org.melekhov.dossier.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaListenerMessage {
    @KafkaListener(topics = "topicName", groupId = "foo")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
