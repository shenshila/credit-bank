package org.melekhov.deal.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic finishRegistration() {
        return new NewTopic("FINISH_REGISTRATION", 2, (short) 2);
    }

    @Bean
    public NewTopic createDocuments() {
        return new NewTopic("CREATE_DOCUMENTS", 2, (short) 2);
    }

    @Bean
    public NewTopic sendDocuments() {
        return new NewTopic("SEND_DOCUMENTS", 2, (short) 2);
    }

    @Bean
    public NewTopic sendSes() {
        return new NewTopic("SEND_SES", 2, (short) 2);
    }

    @Bean
    public NewTopic creditIssued() {
        return new NewTopic("CREDIT_ISSUED", 2, (short) 2);
    }

    @Bean
    public NewTopic statementDenied() {
        return new NewTopic("STATEMENT_DENIED", 2, (short) 2);
    }

}
