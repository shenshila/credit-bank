package org.melekhov.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailMessageServiceImpl {

    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    private final KafkaTemplate<String, String> kafkaTemplateTest;
    public void send(String msg) {
        kafkaTemplateTest.send("baeldung", msg);
    }

//    public void sendCustomMessage(EmailMessageDto emailMessage, UUID statementId) {
//        log.info("Sending Json Serializer: {}", emailMessage);
//
//        kafkaTemplate.send("topic2", emailMessage);
//    }


//    public void sendMessage(String topicName, EmailMessageDto message) {
//        CompletableFuture<SendResult<String, EmailMessageDto>> future = kafkaTemplate.send(topicName, message);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                log.info("Sent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            } else {
//                log.info("Unable to send message=[" +
//                        message + "] due to : " + ex.getMessage());
//            }
//        });
//    }

}
