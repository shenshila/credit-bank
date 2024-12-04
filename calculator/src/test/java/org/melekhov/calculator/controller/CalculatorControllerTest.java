package org.melekhov.calculator.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;
import org.melekhov.calculator.dto.enums.Position;
import org.melekhov.calculator.service.impl.OfferServiceImpl;
import org.melekhov.calculator.service.impl.ScoringServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {

    @Mock
    private OfferServiceImpl offerService;

    @InjectMocks
    private CalculatorController calculatorController;

    @Test
    void createOffersList() {
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

        LoanOfferDto loanOffer = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(110000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9773.37))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        when(offerService.createOffersList(requestDto)).thenReturn(Collections.singletonList(loanOffer));

        ResponseEntity<List<LoanOfferDto>> response = calculatorController.createOffersList(requestDto);

        verify(offerService).createOffersList(requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(BigDecimal.valueOf(110000), response.getBody().get(0).getTotalAmount());
    }

    @Test
    void calculateCredit() {
        EmploymentDto employment = EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("7701000000")
                .salary(BigDecimal.valueOf(73289.45))
                .position(Position.MANAGER)
                .workExperienceTotal(28)
                .workExperienceCurrent(7)
                .build();

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(656666.66))
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

        CreditDto creditDto = CreditDto.builder()
                .amount(BigDecimal.valueOf(456253.27))
                .term(15)
                .monthlyPayment(BigDecimal.valueOf(13223.67))
                .rate(BigDecimal.valueOf(12))
                .psk(BigDecimal.valueOf(78993.23))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        when(offerService.calculateCredit(scoringDataDto)).thenReturn(ResponseEntity.ok(creditDto));

        ResponseEntity<CreditDto> response = calculatorController.calculateCredit(scoringDataDto);

        verify(offerService).calculateCredit(scoringDataDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(creditDto, response.getBody());
    }

}
