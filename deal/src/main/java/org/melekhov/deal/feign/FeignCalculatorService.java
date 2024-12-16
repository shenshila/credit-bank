package org.melekhov.deal.feign;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.melekhov.deal.dto.CreditDto;
import org.melekhov.deal.dto.LoanOfferDto;
import org.melekhov.deal.dto.LoanStatementRequestDto;
import org.melekhov.deal.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
