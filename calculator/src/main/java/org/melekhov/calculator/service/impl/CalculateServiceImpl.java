package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.PaymentScheduleElementDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CalculateService;
import org.melekhov.calculator.service.ScoringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateServiceImpl implements CalculateService {
    @Value("${base.rate}")
    private static BigDecimal BASE_RATE;
    private static final BigDecimal INSURANCE = BigDecimal.valueOf(10000);

    private final ScoringService scoringService;

    @Override
    public BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal baseRate) {
        log.debug("Calculating rate for credit: scoring data: {}, base rate: {}", scoringData, baseRate);

        BigDecimal rate = scoringService.calcRate(scoringData, baseRate);
        log.debug("Result scoring data: {}", rate);
        return rate;
    }

    @Override
    public BigDecimal calcRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.debug("calculating Rate: isInsuranceEnabled: {}, isSalaryClient: {}", isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = BASE_RATE;

        if (isInsuranceEnabled) rate = rate.subtract(BigDecimal.valueOf(3));
        if (isSalaryClient) rate = rate.subtract(BigDecimal.ONE);

        log.info("The rate has been calculated: {}", rate);
        return rate;
    }

    @Override
    public BigDecimal calcMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, Integer term) {
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

    @Override
    public BigDecimal calcTotalAmount(BigDecimal amount, Boolean isInsuranceEnabled) {
        if (isInsuranceEnabled) return amount.add(INSURANCE);

        log.info("Calculated total amount: amount: {}", amount);
        return amount;
    }

    @Override
    public BigDecimal calcPsk(BigDecimal totalAmount, BigDecimal monthlyPayment, Integer term) {
        BigDecimal totalPayment = monthlyPayment.multiply(BigDecimal.valueOf(term));

        log.info("Calculated psk");
        return totalPayment
                .subtract(totalAmount)
                .divide(totalAmount, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public List<PaymentScheduleElementDto> calculatePaymentSchedule(Integer term, BigDecimal totalAmount, BigDecimal rate, BigDecimal monthlyPayment) {
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
