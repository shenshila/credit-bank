package org.melekhov.deal.feign;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.melekhov.deal.dto.LoanOfferDto;
import org.melekhov.deal.dto.LoanStatementRequestDto;
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

    public List<LoanOfferDto> updateLoanOffers(LoanStatementRequestDto request,
                                               UUID statementId) {
        List<LoanOfferDto> loanOffers = getLoanOffers(request);

        for (LoanOfferDto offer : loanOffers) {
            offer.setStatementId(statementId);
        }

        return loanOffers;
    }

}
