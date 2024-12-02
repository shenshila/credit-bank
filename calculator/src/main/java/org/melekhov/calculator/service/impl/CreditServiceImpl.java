package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.PaymentScheduleElementDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CreditService;
import org.melekhov.calculator.service.util.LoanCalculator;
import org.melekhov.calculator.service.util.Scoring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private CreditDto credit;
    private final Scoring scoring = new Scoring();
    private final LoanCalculator calculator;

    @Value("${base.rate}")
    private BigDecimal BASE_RATE;

    @Override
    public ResponseEntity<CreditDto> calc(ScoringDataDto scoringData) {
        return calcCredit(scoringData);
    }

    private ResponseEntity<CreditDto> calcCredit(ScoringDataDto scoringData) {
        credit = new CreditDto();

        credit.setTerm(scoringData.getTerm());
        credit.setIsSalaryClient(scoringData.getIsSalaryClient());
        credit.setIsInsuranceEnabled(scoringData.getIsInsuranceEnabled());

        BigDecimal rate = scoring.calcRate(scoringData, BASE_RATE);
        BigDecimal principal = calculator.calcPrincipal(scoringData.getAmount(), scoringData.getIsInsuranceEnabled()); // Сумма кредита с учетом страховки
        BigDecimal monthlyPayment = calculator.calcMonthlyPayment(principal, rate, scoringData.getTerm());
        BigDecimal totalAmount = calculator.calcTotalAmount(monthlyPayment, scoringData.getTerm());

        credit.setAmount(totalAmount);
        credit.setMonthlyPayment(monthlyPayment);
        credit.setRate(rate);
        credit.setPsk(calcPsk(totalAmount, scoringData.getAmount()));

        createPaymentSchedule(scoringData, principal);

        return ResponseEntity.ok(credit);
    }

    private void createPaymentSchedule(ScoringDataDto scoringData, BigDecimal principal) {
        BigDecimal remainingDebt = principal;
        for (int i = 0; i < credit.getTerm(); i++) {
            LocalDate paymentDate = LocalDate.now().plusMonths(i);
            BigDecimal totalPayment = credit.getMonthlyPayment();
            BigDecimal interestPayment = remainingDebt.multiply(credit.getRate().setScale(2, RoundingMode.HALF_UP));
            BigDecimal debtPayment = credit.getMonthlyPayment().subtract(interestPayment).setScale(2, RoundingMode.HALF_UP);
            remainingDebt = remainingDebt.subtract(debtPayment);

            credit.addPaymentScheduleElement(new PaymentScheduleElementDto(
                    i + 1, paymentDate, totalPayment, interestPayment, debtPayment, remainingDebt));

        }
    }

    private BigDecimal calcPsk(BigDecimal totalAmount, BigDecimal requestedAmount) {
        return totalAmount.subtract(requestedAmount);
    }


}
