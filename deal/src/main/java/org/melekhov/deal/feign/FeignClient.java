package org.melekhov.deal.feign;

import org.melekhov.deal.dto.CreditDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@org.springframework.cloud.openfeign.FeignClient(name = "deal", url = "http://localhost:8080/api/calculator")
public interface FeignClient {

    @PostMapping(value = "/offers")
    List<LoanOfferDto> getLoanOffers(@RequestBody LoanStatementRequestDto request);

    @PostMapping(value = "/calc")
    CreditDto calculateCredit(@RequestBody ScoringDataDto request);

}
