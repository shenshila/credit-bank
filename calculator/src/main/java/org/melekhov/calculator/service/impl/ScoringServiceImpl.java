package org.melekhov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.melekhov.calculator.dto.EmploymentDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.service.ScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    @Value("${base.rate}")
    private BigDecimal finalRate;

    @Override
    public BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal rate) {
        this.finalRate = rate;

        checkEmploymentStatus(scoringData);
        checkPosition(scoringData);
        checkMaritalStatus(scoringData);
        checkGender(scoringData);

        return finalRate;
    }

    private void checkGender(ScoringDataDto scoringData) {
        Period age = Period.between(scoringData.getBirthDate(), LocalDate.now());
        int years = age.getYears();

        if (scoringData.getGender() == Gender.FEMALE && years > 32 && years < 60) {
            finalRate = finalRate.subtract(BigDecimal.valueOf(3));
        } else if (scoringData.getGender() == Gender.MALE && years > 30 && years < 55) {
            finalRate = finalRate.subtract(BigDecimal.valueOf(3));
        } else if (scoringData.getGender() == Gender.UNKNOWN) {
            finalRate = finalRate.add(BigDecimal.valueOf(7));
        }
    }

    private void checkMaritalStatus(ScoringDataDto scoringData) {
        switch (scoringData.getMaritalStatus()) {
            case MARRIED -> finalRate = finalRate.subtract(BigDecimal.valueOf(3));
            case SINGLE -> finalRate = finalRate.add(BigDecimal.ONE);
        }

    }

    private void checkPosition(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        switch (employment.getPosition()) {
            case MANAGER -> finalRate = finalRate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER -> finalRate = finalRate.subtract(BigDecimal.valueOf(3));
        }

    }

    private void checkEmploymentStatus(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        switch (employment.getEmploymentStatus()) {
            case SELF_EMPLOYED -> finalRate = finalRate.add(BigDecimal.valueOf(2));
            case BUSINESS_OWNER -> finalRate = finalRate.add(BigDecimal.valueOf(1));
        }

    }

}
