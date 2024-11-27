package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.service.OfferService;
import org.melekhov.calculator.service.util.LoanCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final LoanCalculator calculator;

    @Value("${base.rate}")
    private BigDecimal BASE_RATE;

    @Override
    public ResponseEntity<List<LoanOfferDto>> getOffers(LoanStatementRequestDto request) {
        return ResponseEntity.ok(createOffers(request));
    }

    private List<LoanOfferDto> createOffers(LoanStatementRequestDto request) {
        List<LoanOfferDto> offers = new ArrayList<>();

        offers.add(createOffer(request, true, true));
        offers.add(createOffer(request, true, false));
        offers.add(createOffer(request, false, true));
        offers.add(createOffer(request, false, false));

        return offers;
    }

    private LoanOfferDto createOffer(LoanStatementRequestDto request,
                                     boolean isInsuranceEnabled,
                                     boolean isSalaryClient) {
        LoanOfferDto offer = new LoanOfferDto();

        offer.setStatementId(UUID.randomUUID());
        offer.setRequestedAmount(request.getAmount());
        offer.setRate(calcRate(isInsuranceEnabled, isSalaryClient));

        BigDecimal principal = calculator.calcPrincipal(request.getAmount(), isInsuranceEnabled);

        offer.setMonthlyPayment(calculator.calcMonthlyPayment(principal, offer.getRate(), offer.getTerm()));
        offer.setTotalAmount(calculator.calcTotalAmount(offer.getMonthlyPayment(), offer.getTerm()));
        offer.setTerm(request.getTerm());
        offer.setIsInsuranceEnabled(isInsuranceEnabled);
        offer.setIsSalaryClient(isSalaryClient);

        return offer;
    }

    private BigDecimal calcRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal rate = BASE_RATE;
        if (isInsuranceEnabled) rate = rate.subtract(new BigDecimal(3));
        if (isSalaryClient) rate = rate.subtract(BigDecimal.ONE);

        return rate;
    }
}
