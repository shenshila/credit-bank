package org.melekhov.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.EmploymentDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;
import org.melekhov.calculator.dto.enums.Position;
import org.melekhov.calculator.service.impl.ScoringServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ScoringServiceImplTest {

    @InjectMocks
    private ScoringServiceImpl scoringService;

    @Test
    void calcRate_adjustsRateBasedOnAmount() {
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
        BigDecimal initialRate = BigDecimal.valueOf(15);

        BigDecimal finalRate = scoringService.calcRate(scoringDataDto, initialRate);

        assertEquals(BigDecimal.valueOf(14), finalRate);


    }

}
