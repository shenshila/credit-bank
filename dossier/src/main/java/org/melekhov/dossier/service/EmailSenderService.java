package org.melekhov.dossier.service;

import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {

    void send(EmailMessageDto emailMessageDto, String text);
    void send(EmailMessageDto emailMessageDto, String text, byte[] pdfContent);

}
