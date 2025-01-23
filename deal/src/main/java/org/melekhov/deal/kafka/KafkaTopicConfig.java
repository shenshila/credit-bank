package org.melekhov.deal.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean NewTopic finishRegistration() {
        return new NewTopic("finish-registration", 1, (short) 1);
    }

    @Bean NewTopic createDocuments() {
        return new NewTopic("create-documents", 1, (short) 1);
    }

    @Bean NewTopic sendDocuments() {
        return new NewTopic("send-documents", 1, (short) 1);
    }

    @Bean NewTopic sendSes() {
        return new NewTopic("send-ses", 1, (short) 1);
    }

    @Bean NewTopic creditIssued() {
        return new NewTopic("credit-issued", 1, (short) 1);
    }

    @Bean NewTopic statementDenied() {
        return new NewTopic("statement-denied", 1, (short) 1);
    }
}
