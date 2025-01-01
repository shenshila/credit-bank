package org.melekhov.statement.Service.impl;

import lombok.RequiredArgsConstructor;
import org.melekhov.statement.Service.StatementService;
import org.melekhov.statement.dto.LoanOfferDto;
import org.melekhov.statement.dto.LoanStatementRequestDto;
import org.melekhov.statement.feign.FeignDealControllerClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final FeignDealControllerClient feignDealControllerClient;

    @Override
    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request) {
        return feignDealControllerClient.calculateLoanOffer(request);
    }

    @Override
    public void selectOffer(LoanOfferDto request) {
        feignDealControllerClient.selectLoanOffer(request);
    }
}
