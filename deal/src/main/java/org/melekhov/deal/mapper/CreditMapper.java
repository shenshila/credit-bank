package org.melekhov.deal.mapper;

import org.melekhov.deal.dto.CreditDto;
import org.melekhov.deal.model.Credit;
import org.melekhov.deal.model.enums.CreditStatus;

import org.springframework.stereotype.Component;

@Component
public class CreditMapper {

    public Credit mapToCredit(CreditDto creditDto) {
        Credit credit = Credit.builder()
                .creditStatus(CreditStatus.CALCULATED)
                .amount(creditDto.getAmount())
                .fullAmount(creditDto.getPsk())
                .rate(creditDto.getRate())
                .term(creditDto.getTerm())
                .isSalaryClient(creditDto.getIsSalaryClient())
                .isInsuranceEnabled(creditDto.getIsInsuranceEnabled())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .build();

        return credit;
    }

}
