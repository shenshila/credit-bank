package org.melekhov.calculator.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class LoanCalculator {

    @Value("${insurance}")
    private BigDecimal INSURANCE;

    public BigDecimal calcMonthlyPayment(BigDecimal principal, BigDecimal rate, Integer term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);

        BigDecimal numerator = principal.multiply(monthlyRate).multiply(BigDecimal.ONE.add(monthlyRate).pow(term));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(term).subtract(BigDecimal.ONE);

        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcPrincipal(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(INSURANCE) : amount;
    }

    public BigDecimal calcTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }
}
