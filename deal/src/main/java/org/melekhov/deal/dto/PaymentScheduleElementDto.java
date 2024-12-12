package org.melekhov.deal.dto;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.time.LocalDate;

//вынести ВСЕ DTOшки в корень
@Embeddable
public class PaymentScheduleElementDto {
    private Integer number;
    private LocalDate paymentDate;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingPayment;
}
