package org.melekhov.deal.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface KafkaDocumentService {

//    public void sendTestMessage(String msg);

    public void sendDocument(UUID statementId);

    public void sendSes(UUID statementId);

    public void creditIssued(UUID statementId);
}
