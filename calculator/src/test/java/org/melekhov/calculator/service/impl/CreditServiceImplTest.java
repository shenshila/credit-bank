package org.melekhov.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.EmploymentDto;
import org.melekhov.calculator.dto.PaymentScheduleElementDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;
import org.melekhov.calculator.dto.enums.Position;
import org.melekhov.calculator.service.util.LoanCalculator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditServiceImplTest {

    @Mock
    LoanCalculator loanCalculator;


    @InjectMocks
    CreditServiceImpl creditService;

    ScoringDataDto scoringData = new ScoringDataDto();
    EmploymentDto employment = new EmploymentDto();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(creditService, "BASE_RATE", new BigDecimal("15"));

        scoringData.setAmount(new BigDecimal(100000));
        scoringData.setTerm(12);
        scoringData.setFirstName("John");
        scoringData.setLastName("Doe");
        scoringData.setMiddleName("Mss");
        scoringData.setGender(Gender.MALE);
        scoringData.setBirthDate(LocalDate.now().minusYears(19));
        scoringData.setPassportSeries("1234");
        scoringData.setPassportNumber("123456");
        scoringData.setPassportIssueDate(LocalDate.now().minusMonths(1));
        scoringData.setPassportIssueBranch("МВД г. Москва");
        scoringData.setMaritalStatus(MaritalStatus.SINGLE);
        scoringData.setDependentAmount(0);

        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employment.setEmployerINN("1234567890");
        employment.setSalary(new BigDecimal(50000));
        employment.setPosition(Position.EMPLOYEE);
        employment.setWorkExperienceTotal(100);
        employment.setWorkExperienceCurrent(20);
        scoringData.setEmployment(employment);
        scoringData.setAccountNumber("1234567890");
        scoringData.setIsInsuranceEnabled(true);
        scoringData.setIsSalaryClient(true);
    }

    @Test
    void calcCredit() {


        when(loanCalculator.calcPrincipal(any(BigDecimal.class), any(Boolean.class)))
                .thenReturn(new BigDecimal("10000"));
        when(loanCalculator.calcMonthlyPayment(any(BigDecimal.class), any(BigDecimal.class), any(Integer.class)))
                .thenReturn(new BigDecimal("8500.00"));
        when(loanCalculator.calcTotalAmount(any(BigDecimal.class), any(Integer.class)))
                .thenReturn(new BigDecimal("100000"));


        ResponseEntity<CreditDto> response = creditService.calc(scoringData);


        CreditDto creditDto = response.getBody();
        assertEquals(scoringData.getTerm(), creditDto.getTerm());
        assertEquals(scoringData.getAmount(), creditDto.getAmount());
        assertEquals(scoringData.getIsInsuranceEnabled(), creditDto.getIsInsuranceEnabled());
        assertEquals(scoringData.getIsSalaryClient(), creditDto.getIsSalaryClient());
        assertEquals(new BigDecimal("8500.00"), creditDto.getMonthlyPayment());
        assertEquals(new BigDecimal("100000"), creditDto.getAmount());


        List<PaymentScheduleElementDto> paymentSchedule = creditDto.getPaymentSchedule();
        assertEquals(scoringData.getTerm(), paymentSchedule.size());
    }

    @Test
    void creditRefusal() {

        scoringData.setBirthDate(LocalDate.now().minusYears(17));
        assertThrows(NullPointerException.class, () -> creditService.calc(scoringData));
        scoringData.setBirthDate(LocalDate.now().minusYears(19));

        employment.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
        assertThrows(IllegalArgumentException.class, () -> creditService.calc(scoringData));
        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);

        scoringData.setAmount(new BigDecimal("500000000"));
        assertThrows(NullPointerException.class, () -> creditService.calc(scoringData));
        scoringData.setAmount(new BigDecimal("100000"));

        employment.setWorkExperienceCurrent(2);
        assertThrows(IllegalArgumentException.class, () -> creditService.calc(scoringData));
        employment.setWorkExperienceCurrent(3);

        employment.setWorkExperienceTotal(17);
        assertThrows(IllegalArgumentException.class, () -> creditService.calc(scoringData));
        employment.setWorkExperienceTotal(18);

    }

}
