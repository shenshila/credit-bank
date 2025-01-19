package org.melekhov.dossier.service;

import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {

    public void send(EmailMessageDto emailMessageDto, String text);

}
