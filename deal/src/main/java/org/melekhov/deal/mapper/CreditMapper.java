package org.melekhov.deal.mapper;

import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.dto.CreditDto;
import org.melekhov.deal.model.Credit;
import org.melekhov.deal.model.enums.CreditStatus;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditMapper {

    public Credit mapToCredit(CreditDto creditDto) {
        log.info("Creating new credit with creditDto: {}", creditDto);
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
