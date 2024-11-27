package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.service.CreditService;
import org.melekhov.calculator.service.util.LoanCalculator;
import org.melekhov.calculator.service.util.Scoring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

        credit.setTerm(scoringData.getTerm()); // Срок кредита
        credit.setIsSalaryClient(scoringData.getIsSalaryClient()); // Страхование
        credit.setIsInsuranceEnabled(scoringData.getIsInsuranceEnabled()); // Клиент банка

        BigDecimal rate = scoring.calcRate(scoringData, BASE_RATE);
        BigDecimal principal = calculator.calcPrincipal(scoringData.getAmount(), scoringData.getIsInsuranceEnabled()); // Сумма кредита с учетом страховки
        BigDecimal monthlyPayment = calculator.calcMonthlyPayment(principal, rate, scoringData.getTerm());
        BigDecimal totalAmount = calculator.calcTotalAmount(monthlyPayment, scoringData.getTerm());

        credit.setAmount(totalAmount); // Сумма кредита
        credit.setMonthlyPayment(monthlyPayment); // Ежемесячный платеж
        credit.setRate(rate); // Ставка
        credit.setPsk(calcPsk(totalAmount, scoringData.getAmount())); // Сумма затрат заемщика
//        credit.setPaymentSchedule(createPaymentSchedule()); // График платежей

        return ResponseEntity.ok(credit);
    }

    private BigDecimal calcPsk(BigDecimal totalAmount, BigDecimal requestedAmount) {
        return totalAmount.subtract(requestedAmount);
    }


}
