package org.melekhov.gateway.feign;

import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "statementClient", url = "${app.url.statement}")
public interface FeignStatementClient {

    @PostMapping()
    ResponseEntity<List<LoanOfferDto>> generateLoanOffers(@RequestBody LoanStatementRequestDto loanStatement);

    @PostMapping("/offer")
    ResponseEntity<Void> selectOffer(@RequestBody LoanOfferDto loanOffer);


}
