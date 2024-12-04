package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.service.OfferService;
import org.melekhov.calculator.service.ScoringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    ScoringService scoringService;

    @Value("${base.rate}")
    private BigDecimal BASE_RATE;
    private BigDecimal INSURANCE = BigDecimal.valueOf(10000);

    @Override
    public ResponseEntity<CreditDto> calculateCredit(ScoringDataDto scoringData) {
        log.info("Starting calculate credit for scoring data: {}", scoringData);

        BigDecimal rate = calcRate(scoringData, BASE_RATE);
        BigDecimal totalAmount = calcTotalAmount(scoringData.getAmount(), scoringData.getIsInsuranceEnabled());
        BigDecimal monthlyPayment = calcMonthlyPayment(totalAmount, rate, scoringData.getTerm());
        BigDecimal psk = calcPsk(totalAmount, monthlyPayment, scoringData.getTerm());
        List<PaymentScheduleElementDto> paymentSchedule = calculatePaymentSchedule(scoringData.getTerm(), totalAmount, rate, monthlyPayment);

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

        return ResponseEntity.ok(credit);
    }

    @Override
    public List<LoanOfferDto> createOffersList(LoanStatementRequestDto request) {
        log.info("Starting calculation of loan offers for request: {}", request);
        List<LoanOfferDto> offers = new ArrayList<>();

        offers.add(generateLoanOffer(request, true, true));
        offers.add(generateLoanOffer(request, true, false));
        offers.add(generateLoanOffer(request, false, true));
        offers.add(generateLoanOffer(request, false, false));

        return offers;
    }

    private LoanOfferDto generateLoanOffer(LoanStatementRequestDto request, boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.info("Creating loan offer with parameters: isInsuranceEnabled: {} isSalaryClient: {}", isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = calcRate(isInsuranceEnabled, isSalaryClient);
        BigDecimal totalAmount = calcTotalAmount(request.getAmount(), isInsuranceEnabled);
        BigDecimal monthlyPayment = calcMonthlyPayment(totalAmount, rate, request.getTerm());

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

    private BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal baseRate) {
        log.debug("Calculating rate for credit: scoring data: {}, base rate: {}", scoringData, baseRate);

        scoringService = new ScoringServiceImpl();

        BigDecimal rate = scoringService.calcRate(scoringData, baseRate);
        log.debug("Result scoring data: {}", rate);
        return rate;
    }

    private BigDecimal calcRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.debug("calculating Rate: isInsuranceEnabled: {}, isSalaryClient: {}", isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = BASE_RATE;

        if (isInsuranceEnabled) rate = rate.subtract(BigDecimal.valueOf(3));
        if (isSalaryClient) rate = rate.subtract(BigDecimal.ONE);

        log.info("The rate has been calculated: {}", rate);
        return rate;
    }

    private BigDecimal calcMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, Integer term) {
        log.debug("Calculating monthly payment: totalAmount: {}, rate: {}, term: {}", totalAmount, rate, term);

        BigDecimal monthlyRate = rate
                .divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        double pow = Math.pow(1 + monthlyRate.doubleValue(), term);
        BigDecimal numerator = totalAmount.multiply(monthlyRate)
                .multiply(BigDecimal.valueOf(pow));
        BigDecimal denominator = BigDecimal.valueOf(pow - 1);

        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        log.debug("The monthly payment result: {}", monthlyPayment);
        return monthlyPayment;
    }

    private BigDecimal calcTotalAmount(BigDecimal amount, Boolean isInsuranceEnabled) {
        if (isInsuranceEnabled) return amount.add(INSURANCE);

        log.info("Calculated total amount: amount: {}", amount);
        return amount;
    }

    private BigDecimal calcPsk(BigDecimal totalAmount, BigDecimal monthlyPayment, Integer term) {
        BigDecimal totalPayment = monthlyPayment.multiply(BigDecimal.valueOf(term));

        log.info("Calculated psk");
        return totalPayment
                .subtract(totalAmount)
                .divide(totalAmount, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private List<PaymentScheduleElementDto> calculatePaymentSchedule(Integer term, BigDecimal totalAmount, BigDecimal rate, BigDecimal monthlyPayment) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        BigDecimal monthlyRate = rate
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN);
        BigDecimal remainingDebt = totalAmount;

        for (int i = 1; i <= term; i++) {
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment).setScale(2, RoundingMode.HALF_EVEN);

            remainingDebt = remainingDebt.subtract(debtPayment).setScale(2, RoundingMode.HALF_EVEN);

            PaymentScheduleElementDto elementDto = PaymentScheduleElementDto.builder()
                    .number(i)
                    .date(LocalDate.now().plusMonths(i))
                    .debtPayment(debtPayment)
                    .interestPayment(interestPayment)
                    .totalPayment(monthlyPayment)
                    .remainingDebt(remainingDebt)
                    .build();

            paymentSchedule.add(elementDto);
        }

        log.info("Calculated payment schedule: {}", paymentSchedule);
        return paymentSchedule;
    }

}
