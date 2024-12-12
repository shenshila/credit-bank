package org.melekhov.deal.model;

import jakarta.persistence.*;
import lombok.*;
import org.melekhov.deal.dto.PaymentScheduleElementDto;
import org.melekhov.deal.model.enums.CreditStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Entity
@Table(name = "credit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_id")
    private UUID clientId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal fullAmount;

    @Column(name = "payment_schedule")
    private PaymentScheduleElementDto paymentSchedule;

    @Column(name = "insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    private CreditStatus creditStatus;
}
