package org.melekhov.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;
import org.melekhov.calculator.dto.enums.Position;
import org.melekhov.calculator.service.impl.OfferServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(offerService, "BASE_RATE", BigDecimal.valueOf(15));
    }

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
                .position(Position.MANAGER)
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

        ResponseEntity<CreditDto> response = offerService.calculateCredit(scoringDataDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CreditDto credit = response.getBody();
        assertNotNull(credit);
        assertEquals(BigDecimal.valueOf(660000.0), credit.getAmount());
        assertEquals(19, credit.getTerm());
    }

}
