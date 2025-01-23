package org.melekhov.deal.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface KafkaDocumentService {

    public void finishRegistration(UUID statementId);

    public void createDocuments(UUID statementId);

    public void sendDocuments(UUID statementId);

    public void sendSes(UUID statementId);

    public void creditIssued(UUID statementId);

    public void statementDenied(UUID statementId);
}
