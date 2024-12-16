package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.service.CalculateService;
import org.melekhov.calculator.service.OfferService;
import org.melekhov.calculator.service.UserValidService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final UserValidService userValidService;
    private final CalculateService calculateService;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        log.info("Starting calculate credit for scoring data: {}", scoringData);
        userValidService.checkClientSolvency(scoringData);

        System.out.println("amount: " + scoringData.getAmount());
        System.out.println("term: " + scoringData.getTerm());
        System.out.println("firstName: " + scoringData.getFirstName());
        System.out.println("lastName: " + scoringData.getLastName());
        System.out.println("middleName: " + scoringData.getMiddleName());
        System.out.println("gender: " + scoringData.getGender());
        System.out.println("birthDate: " + scoringData.getBirthDate());
        System.out.println("passportSeries: " + scoringData.getPassportSeries());
        System.out.println("passportNumber: " + scoringData.getPassportNumber());
        System.out.println("passportIssueDate: " + scoringData.getPassportIssueDate());
        System.out.println("passportIssueBranch: " + scoringData.getPassportIssueBranch());
        System.out.println("maritalStatus: " + scoringData.getMaritalStatus());
        System.out.println("dependentAmount: " + scoringData.getDependentAmount());
        System.out.println("employmentStatus: " + scoringData.getEmployment().getEmploymentStatus());
        System.out.println("INN: " + scoringData.getEmployment().getEmployerINN());
        System.out.println("position: " + scoringData.getEmployment().getPosition());
        System.out.println("salary: " + scoringData.getEmployment().getSalary());
        System.out.println("workExperienceTotal: " + scoringData.getEmployment().getWorkExperienceTotal());
        System.out.println("workExperienceCurrent: " + scoringData.getEmployment().getWorkExperienceCurrent());
        System.out.println("accountNumber: " + scoringData.getAccountNumber());
        System.out.println("isInsuranceEnabled: " + scoringData.getIsInsuranceEnabled());
        System.out.println("isSalaryClient: " + scoringData.getIsSalaryClient());

        log.debug("Starting calculate credit for scoring data: {}", scoringData);
        BigDecimal rate = calculateService.calcRate(scoringData, calculateService.calcRate(scoringData.getIsInsuranceEnabled(), scoringData.getIsSalaryClient()));
        BigDecimal totalAmount = calculateService.calcTotalAmount(scoringData.getAmount(), scoringData.getIsInsuranceEnabled());
        BigDecimal monthlyPayment = calculateService.calcMonthlyPayment(totalAmount, rate, scoringData.getTerm());
        BigDecimal psk = calculateService.calcPsk(totalAmount, monthlyPayment, scoringData.getTerm());
        List<PaymentScheduleElementDto> paymentSchedule = calculateService.calculatePaymentSchedule(scoringData.getTerm(), totalAmount, rate, monthlyPayment);

        CreditDto credit = CreditDto.builder()
                .amount(totalAmount)
                .term(scoringData.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isSalaryClient(scoringData.getIsSalaryClient())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .paymentSchedule(paymentSchedule)
                .build();

        return credit;
    }

    @Override
    public List<LoanOfferDto> createOffersList(LoanStatementRequestDto request) {
        log.info("Starting calculation of loan offers for request: {}", request);
        userValidService.checkClientSolvency(request);

        List<LoanOfferDto> offers = new ArrayList<>();

        offers.add(generateLoanOffer(request, true, true));
        offers.add(generateLoanOffer(request, true, false));
        offers.add(generateLoanOffer(request, false, true));
        offers.add(generateLoanOffer(request, false, false));

        return offers;
    }

    private LoanOfferDto generateLoanOffer(LoanStatementRequestDto request, boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.info("Creating loan offer with parameters: isInsuranceEnabled: {} isSalaryClient: {}", isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = calculateService.calcRate(isInsuranceEnabled, isSalaryClient);
        BigDecimal totalAmount = calculateService.calcTotalAmount(request.getAmount(), isInsuranceEnabled);
        BigDecimal monthlyPayment = calculateService.calcMonthlyPayment(totalAmount, rate, request.getTerm());

        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }

}
