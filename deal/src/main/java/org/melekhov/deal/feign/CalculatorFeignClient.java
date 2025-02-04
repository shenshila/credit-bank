package org.melekhov.deal.feign;

import org.melekhov.deal.dto.CreditDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "deal", url = "${other.service.calculator.url}")
public interface CalculatorFeignClient {

    @PostMapping(value = "/offers")
    List<LoanOfferDto> getLoanOffers(@RequestBody LoanStatementRequestDto request);

    @PostMapping(value = "/calc")
    CreditDto calculateCredit(@RequestBody ScoringDataDto request);

}
