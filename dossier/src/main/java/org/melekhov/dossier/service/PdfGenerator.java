package org.melekhov.dossier.service;

import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PdfGenerator {
    byte[] generateStatementPdf(EmailMessageDto msg);
}
