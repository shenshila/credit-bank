package org.melekhov.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.service.impl.OfferServiceImpl;
import org.melekhov.calculator.service.impl.UserValidServiceImpl;
import org.melekhov.shareddto.dto.*;
import org.melekhov.shareddto.enums.EmploymentStatus;
import org.melekhov.shareddto.enums.Gender;
import org.melekhov.shareddto.enums.MaritalStatus;
import org.melekhov.shareddto.enums.Position;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private UserValidService userValidService;

    @Mock
    private CalculateService calculateService;

    @Test
    void createOffersList_generatesFourOffers() {
        LoanStatementRequestDto requestDto = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivanov@gmail.com")
                .birthDate(LocalDate.now().minusYears(22))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();

        // Мокаем вызовы
        Mockito.when(calculateService.calcRate(Mockito.anyBoolean(), Mockito.anyBoolean()))
                .thenReturn(BigDecimal.valueOf(12));
        Mockito.when(calculateService.calcTotalAmount(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(BigDecimal.valueOf(110000));
        Mockito.when(calculateService.calcMonthlyPayment(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(BigDecimal.valueOf(10000));

        List<LoanOfferDto> offers = offerService.createOffersList(requestDto);

        assertEquals(4, offers.size());
        assertNotNull(offers.get(0).getStatementId());
    }

    @Test
    void calculateCredit_success() {
        EmploymentDto employment = EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("7701000000")
                .salary(BigDecimal.valueOf(73289.45))
                .position(Position.WORKER)
                .workExperienceTotal(28)
                .workExperienceCurrent(7)
                .build();

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(650000.00))
                .term(19)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иваныч")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now().minusYears(22))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.parse("2020-01-22"))
                .passportIssueBranch("ОВД Центрального района")
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(4)
                .employment(employment)
                .accountNumber("1231231231")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        // Мокаем вызовы
        Mockito.doNothing().when(userValidService).checkClientSolvency(scoringDataDto);
        Mockito.when(calculateService.calcRate(Mockito.anyBoolean(), Mockito.anyBoolean()))
                .thenReturn(BigDecimal.valueOf(13.5));
        Mockito.when(calculateService.calcTotalAmount(Mockito.any(), Mockito.anyBoolean()))
                .thenReturn(BigDecimal.valueOf(660000.00));
        Mockito.when(calculateService.calcMonthlyPayment(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(BigDecimal.valueOf(35000.00));
        Mockito.when(calculateService.calcPsk(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(BigDecimal.valueOf(14.0));
        Mockito.when(calculateService.calculatePaymentSchedule(Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());

        CreditDto credit = offerService.calculateCredit(scoringDataDto);

        assertNotNull(credit);
        assertEquals(BigDecimal.valueOf(660000.0), credit.getAmount());
        assertEquals(19, credit.getTerm());
    }

}

