package org.melekhov.gateway.feign;

import org.melekhov.shareddto.dto.FinishRegistrationRequestDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Component
@FeignClient(name = "dealClient", url = "${app.url.deal}")
public interface FeignDealClient {

    @PostMapping("/calculate/{statementId}")
    ResponseEntity<Void> calculateCredit(@PathVariable("statementId") UUID statementId,
                                                @RequestBody FinishRegistrationRequestDto finishRegistrationRequest);

    @PostMapping(value = "/document/{statementId}/send")
    ResponseEntity<Void> sendDocument(@PathVariable UUID statementId);

    @PostMapping(value = "/document/{statementId}/sign")
    ResponseEntity<Void> signDocument(@PathVariable UUID statementId);

    @PostMapping(value = "/document/{statementId}/code")
    ResponseEntity<Void> codeDocument(@PathVariable UUID statementId);

}
