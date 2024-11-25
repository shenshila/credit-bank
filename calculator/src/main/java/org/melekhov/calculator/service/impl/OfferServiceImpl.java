package org.melekhov.calculator.service.impl;

import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.service.OfferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {
    @Value("${base.rate}")
    private BigDecimal BASE_RATE;
    @Value("${insurance}")
    private BigDecimal INSURANCE;

    @Override
    public ResponseEntity<List<LoanOfferDto>> getOffers(LoanStatementRequestDto request) {
        return ResponseEntity.ok(createOffers(request));
    }

//    Прескоринг
//    Правила прескоринга (можно придумать новые правила или изменить существующие):
//
//    Имя, Фамилия - от 2 до 30 латинских букв. Отчество, при наличии - от 2 до 30 латинских букв.
//    Сумма кредита - действительно число, большее или равное 20000.
//    Срок кредита - целое число, большее или равное 6.
//    Дата рождения - число в формате гггг-мм-дд, не позднее 18 лет с текущего дня.
//    Email адрес - строка, подходящая под паттерн ^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$
//    Серия паспорта - 4 цифры, номер паспорта - 6 цифр.

    private List<LoanOfferDto> createOffers(LoanStatementRequestDto request) {
        List<LoanOfferDto> offers = new ArrayList<>();

        offers.add(createOffer(request, true, true));
        offers.add(createOffer(request, true, false));
        offers.add(createOffer(request, false, true));
        offers.add(createOffer(request, false, false));

        return offers;
    }

//     4 предложения (сущность "LoanOffer") по кредиту с разными условиями (например без страховки,
//     со страховкой, с зарплатным клиентом, со страховкой и зарплатным клиентом) или отказ

    private LoanOfferDto createOffer(LoanStatementRequestDto request,
                                     boolean isInsuranceEnabled,
                                     boolean isSalaryClient) {
        LoanOfferDto offer = new LoanOfferDto();

        offer.setStatementId(UUID.randomUUID());
        offer.setRequestedAmount(request.getAmount());
        offer.setRate(calcRate(isInsuranceEnabled, isSalaryClient));
        offer.setTotalAmount(calcTotalAmount(offer.getRequestedAmount(), offer.getRate(), isInsuranceEnabled));
        offer.setTerm(request.getTerm());
        offer.setMonthlyPayment(calcMonthlyPayment(offer.getTotalAmount(), offer.getTerm()));
        offer.setIsInsuranceEnabled(isInsuranceEnabled);
        offer.setIsSalaryClient(isSalaryClient);

        return offer;
    }

    private BigDecimal calcMonthlyPayment(BigDecimal totalAmount, Integer term) {
        return totalAmount.divide(new BigDecimal(term), 2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calcRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal rate = BASE_RATE;
        if (isInsuranceEnabled) rate = rate.subtract(new BigDecimal(3));
        if (isSalaryClient) rate = rate.subtract(BigDecimal.ONE);

        return rate;
    }

    private BigDecimal calcTotalAmount(BigDecimal amount, BigDecimal rate, boolean isInsuranceEnabled) {
        BigDecimal totalAmount = amount;
        totalAmount = totalAmount.multiply(rate);
        if (isInsuranceEnabled) totalAmount = totalAmount.add(INSURANCE);

        return totalAmount;
    }


}
