package org.melekhov.deal.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

//    @Bean
//    public NewTopic testTopic() {
//        return new NewTopic("test", 1, (short) 1);
//    }

//    @Bean NewTopic messageTopic() {
//        return new NewTopic("messages", 1, (short) 1);
//    }

    @Bean NewTopic finishRegistration() {
        return new NewTopic("finish-registration", 1, (short) 1);
    }

    @Bean NewTopic sendSes() {
        return new NewTopic("send-ses", 1, (short) 1);
    }

    @Bean NewTopic creditIssued() {
        return new NewTopic("credit-issued", 1, (short) 1);
    }
}
