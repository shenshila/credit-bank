package org.melekhov.statement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.statement.service.PreScoringService;
import org.melekhov.statement.service.StatementService;
import org.melekhov.statement.feign.FeignDealControllerClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementServiceImpl implements StatementService {

    private final PreScoringService preScoringService;
    private final FeignDealControllerClient feignDealControllerClient;

    @Override
    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request) {
        log.info("Received request to generate loan offers: {}", request);

        try {
            preScoringService.preScoring(request);
            log.info("Pre-scoring completed successfully for request: {}", request);
        } catch (IllegalArgumentException ex) {
            log.error("Pre-scoring failed for request: {}. Reason: {}", request, ex.getMessage());
            throw ex;
        }

        List<LoanOfferDto> loanOffers = feignDealControllerClient.calculateLoanOffer(request);
        log.info("Generated loan offers: {} for request: {}", loanOffers, request);

        return loanOffers;
    }

    @Override
    public void selectOffer(LoanOfferDto request) {
        log.info("Received request to select loan offer: {}", request);

        try {
            feignDealControllerClient.selectLoanOffer(request);
            log.info("Loan offer successfully selected: {}", request);
        } catch (Exception ex) {
            log.error("Failed to select loan offer: {}. Reason: {}", request, ex.getMessage());
            throw ex;
        }
    }
}
