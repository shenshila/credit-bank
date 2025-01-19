package org.melekhov.dossier.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.dossier.service.EmailSenderService;
import org.melekhov.shareddto.dto.EmailMessageDto;
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
        simpleMailMessage.setSubject(text);
        simpleMailMessage.setText(String.valueOf(emailMessageDto.getStatementId()));

        mailSender.send(simpleMailMessage);
        log.info("Email send to {} with subject {}", emailMessageDto.getAddress(), text);
    }

//    public void sendEmailWithAttachment(EmailMessageDto emailMessageDto, String text) throws MessagingException, FileNotFoundException {
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            messageHelper.setFrom("${spring.mail.username}");
//            messageHelper.setTo(emailMessageDto.getAddress());
//            messageHelper.setSubject(text);
//            messageHelper.setText(String.valueOf(emailMessageDto.getStatementId()));
//
//            FileSystemResource file = new File();
//            messageHelper.addAttachment("Purchase Order", file);
//
//            mailSender.send(mimeMessage);
//            log.info("Email send to {} with subject {}", emailMessageDto.getAddress(), text);
//        } catch (jakarta.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
