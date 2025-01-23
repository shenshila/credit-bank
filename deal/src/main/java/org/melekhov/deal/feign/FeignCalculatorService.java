package org.melekhov.deal.feign;

import org.melekhov.deal.dto.CreditDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeignCalculatorService {

    private FeignClient feignClient;

    public FeignCalculatorService(FeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request) {
        return feignClient.getLoanOffers(request);
    }

    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        return feignClient.calculateCredit(scoringData);
    }

}
