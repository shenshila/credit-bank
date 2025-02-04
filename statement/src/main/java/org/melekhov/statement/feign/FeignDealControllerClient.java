package org.melekhov.statement.feign;

import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "statement", url = "${other.service.deal.url}")
public interface FeignDealControllerClient {

    @PostMapping("/statement")
    List<LoanOfferDto> calculateLoanOffer(@RequestBody LoanStatementRequestDto request);

    @PostMapping("/offer/select")
    void selectLoanOffer(@RequestBody LoanOfferDto request);
}

