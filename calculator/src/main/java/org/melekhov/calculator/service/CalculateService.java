package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.PaymentScheduleElementDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface CalculateService {
    BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal baseRate);

    BigDecimal calcRate(boolean isInsuranceEnabled, boolean isSalaryClient);

    BigDecimal calcMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, Integer term);

    BigDecimal calcTotalAmount(BigDecimal amount, Boolean isInsuranceEnabled);

    BigDecimal calcPsk(BigDecimal totalAmount, BigDecimal monthlyPayment, Integer term);

    List<PaymentScheduleElementDto> calculatePaymentSchedule(Integer term, BigDecimal totalAmount, BigDecimal rate, BigDecimal monthlyPayment);
}
