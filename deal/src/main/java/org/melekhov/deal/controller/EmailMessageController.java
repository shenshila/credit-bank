package org.melekhov.deal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.service.impl.EmailMessageServiceImpl;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/deal/document")
public class EmailMessageController {

    private final EmailMessageServiceImpl emailMessageService;

    @PostMapping("/send")
    public String send(@RequestBody String msg) {
        emailMessageService.send(msg);
        return "OK";
    }

//    @PostMapping("/{statementId}/send")
//    public ResponseEntity<Void> sendDocument(@PathVariable UUID statementId, @RequestBody EmailMessageDto emailMessageDto) {
//        emailMessageService.sendCustomMessage(emailMessageDto, statementId);
//        return ResponseEntity.ok().build();
//    }

}
