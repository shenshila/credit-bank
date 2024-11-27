package org.melekhov.calculator.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreditDto {
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

    public void addPaymentScheduleElement(PaymentScheduleElementDto paymentScheduleElement) {
        this.paymentSchedule.add(paymentScheduleElement);
    }
}
