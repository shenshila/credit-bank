package org.melekhov.calculator.service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoanCalculatorTest {

    @Value("${insurance}")
    private BigDecimal INSURANCE;

    private final LoanCalculator loanCalculator = new LoanCalculator();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loanCalculator, "INSURANCE", new BigDecimal("10000"));
    }

    @Test
    void calculateMonthlyPayment() {
        BigDecimal loanAmount = BigDecimal.valueOf(100000);
        BigDecimal interestRate = BigDecimal.valueOf(10.5);
        int term = 12;

        BigDecimal monthlyPayment = loanCalculator.calcMonthlyPayment(loanAmount, interestRate, term);
        assertEquals(BigDecimal.valueOf(8817.66).setScale(2, RoundingMode.HALF_UP), monthlyPayment);
    }

    @Test
    void calcPrincipal_withInsurance() {
        BigDecimal amount = BigDecimal.valueOf(100000);
        boolean isInsuranceEnabled = true;
        BigDecimal expectedPrincipal = BigDecimal.valueOf(110000);
        BigDecimal actualPrincipal = loanCalculator.calcPrincipal(amount, isInsuranceEnabled);
        assertEquals(expectedPrincipal, actualPrincipal);
    }

    @Test
    void calculateTotalAmount() {
        BigDecimal monthlyPayment = BigDecimal.valueOf(8600);
        int term = 12;

        BigDecimal totalAmount = loanCalculator.calcTotalAmount(monthlyPayment, term);
        assertEquals(monthlyPayment.multiply(BigDecimal.valueOf(term)), totalAmount);
    }

}
