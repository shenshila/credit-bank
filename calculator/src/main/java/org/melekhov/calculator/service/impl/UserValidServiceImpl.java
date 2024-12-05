package org.melekhov.calculator.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.service.UserValidService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidServiceImpl implements UserValidService {
    @Override
    public void checkClientSolvency(ScoringDataDto scoringDataDto) {
        log.info("Check client solvency");
        checkWorkingStatus(scoringDataDto.getEmployment().getEmploymentStatus());
        checkLoanAmount(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary());
        checkAge(scoringDataDto.getBirthDate());
        checkTotalWorkExperience(scoringDataDto.getEmployment().getWorkExperienceTotal());
        checkCurrentWorkExperience(scoringDataDto.getEmployment().getWorkExperienceCurrent());
        log.info("Check client solvency completed");
    }

    @Override
    public void checkClientSolvency(LoanStatementRequestDto loanStatement) {
        log.info("Check client solvency");
        checkAge(loanStatement.getBirthDate());
        log.info("Check client solvency completed");
    }

    private void checkCurrentWorkExperience(Integer workExperienceCurrent) {
        if (workExperienceCurrent < 3) throw new IllegalArgumentException("Current work experience should not be less than 3 months");
    }

    private void checkTotalWorkExperience(Integer workExperienceTotal) {
        if (workExperienceTotal < 18) throw new IllegalArgumentException("Total work experience should not be less than 18 months");
    }

    private void checkAge(LocalDate birthDate) {
        Period age = Period.between(birthDate, LocalDate.now());

        if (age.getYears() < 20 && age.getYears() > 60) {
            throw new IllegalArgumentException("Age must be between 20 and 60");
        }
    }

    private void checkLoanAmount(BigDecimal amount, BigDecimal salary) {
        if (amount.compareTo(salary) < 0) {
            throw new IllegalArgumentException("Amount must be greater than 24 salary");
        }
    }

    private void checkWorkingStatus(EmploymentStatus employmentStatus) {
        if (employmentStatus == EmploymentStatus.UNEMPLOYED) throw new IllegalArgumentException("Employment status should not be unemployed");
    }
}
