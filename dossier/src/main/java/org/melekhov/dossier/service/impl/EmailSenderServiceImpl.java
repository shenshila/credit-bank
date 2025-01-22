package org.melekhov.dossier.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.dossier.service.EmailSenderService;
import org.melekhov.dossier.service.PdfGenerator;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public void send(EmailMessageDto emailMessageDto, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("${spring.mail.username}");
        simpleMailMessage.setTo(emailMessageDto.getAddress());
        simpleMailMessage.setSubject(emailMessageDto.getTheme().toString());
        simpleMailMessage.setText(text);

        mailSender.send(simpleMailMessage);
        log.info("Email send to {} with subject {}", emailMessageDto.getAddress(), text);
    }

    @Override
    public void send(EmailMessageDto msg, String text, byte[] pdfContent) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("${spring.mail.username}");
            helper.setTo(msg.getAddress());
            helper.setSubject(text);
            helper.setText(String.valueOf(msg.getStatementId()));

            helper.addAttachment("LoanStatement.pdf", new ByteArrayResource(pdfContent));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
