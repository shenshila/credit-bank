package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.service.CalculateService;
import org.melekhov.calculator.service.OfferService;
import org.melekhov.calculator.service.UserValidService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final UserValidService userValidService;
    private final CalculateService calculateService;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        log.info("Starting calculate credit for scoring data: {}", scoringData);
        userValidService.checkClientSolvency(scoringData);

        log.debug("Starting calculate credit for scoring data: {}", scoringData);
        BigDecimal rate = calculateService.calcRate(scoringData, calculateService.calcRate(scoringData.getIsInsuranceEnabled(), scoringData.getIsSalaryClient()));
        BigDecimal totalAmount = calculateService.calcTotalAmount(scoringData.getAmount(), scoringData.getIsInsuranceEnabled());
        BigDecimal monthlyPayment = calculateService.calcMonthlyPayment(totalAmount, rate, scoringData.getTerm());
        BigDecimal psk = calculateService.calcPsk(totalAmount, monthlyPayment, scoringData.getTerm());
        List<PaymentScheduleElementDto> paymentSchedule = calculateService.calculatePaymentSchedule(scoringData.getTerm(), totalAmount, rate, monthlyPayment);

        CreditDto credit = CreditDto.builder()
                .amount(totalAmount)
                .term(scoringData.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isSalaryClient(scoringData.getIsSalaryClient())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .paymentSchedule(paymentSchedule)
                .build();

        return credit;
    }

    @Override
    public List<LoanOfferDto> createOffersList(LoanStatementRequestDto request) {
        log.info("Starting calculation of loan offers for request: {}", request);
        userValidService.checkClientSolvency(request);

        List<LoanOfferDto> offers = new ArrayList<>();

        offers.add(generateLoanOffer(request, true, true));
        offers.add(generateLoanOffer(request, true, false));
        offers.add(generateLoanOffer(request, false, true));
        offers.add(generateLoanOffer(request, false, false));

        return offers;
    }

    private LoanOfferDto generateLoanOffer(LoanStatementRequestDto request, boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.info("Creating loan offer with parameters: isInsuranceEnabled: {} isSalaryClient: {}", isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = calculateService.calcRate(isInsuranceEnabled, isSalaryClient);
        BigDecimal totalAmount = calculateService.calcTotalAmount(request.getAmount(), isInsuranceEnabled);
        BigDecimal monthlyPayment = calculateService.calcMonthlyPayment(totalAmount, rate, request.getTerm());

        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }

}
