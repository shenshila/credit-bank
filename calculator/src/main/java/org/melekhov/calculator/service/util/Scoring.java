package org.melekhov.calculator.service.util;

import org.melekhov.calculator.dto.EmploymentDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.melekhov.calculator.dto.enums.Gender;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class Scoring {

    @Value("${base.rate}")
    private BigDecimal finalRate;

    public BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal rate) {

        this.finalRate = rate;

        checkEmploymentStatus(scoringData);
        checkPosition(scoringData);
        checkAmount(scoringData);
        checkMaritalStatus(scoringData);
        checkAge(scoringData);
        checkGender(scoringData);
        checkWorkExperience(scoringData);

        return finalRate;
    }

    private void checkWorkExperience(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        if (employment.getWorkExperienceTotal() < 18 || employment.getWorkExperienceCurrent() < 3) {
            throw new IllegalArgumentException("xxhxsdf");
        }
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

    private void checkAge(ScoringDataDto scoringData) {
        Period age = Period.between(scoringData.getBirthDate(), LocalDate.now());

        if (age.getYears() < 20 && age.getYears() > 60) {
            throw new IllegalArgumentException("Age must be between 20 and 60");
        }
    }

    private void checkMaritalStatus(ScoringDataDto scoringData) {
        switch (scoringData.getMaritalStatus()) {
            case MARRIED -> finalRate.subtract(BigDecimal.valueOf(3));
            case SINGLE -> finalRate.add(BigDecimal.ONE);
        }

    }

    private void checkAmount(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        BigDecimal amount = scoringData.getAmount();
        BigDecimal salary = employment.getSalary();

        if (amount.compareTo(salary) < 0) {
            throw new IllegalArgumentException("Amount must be greater than 24 salary");
        }
    }

    private void checkPosition(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        switch (employment.getPosition()) {
            case MANAGER -> finalRate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER -> finalRate.subtract(BigDecimal.valueOf(3));
        }

    }

    private void checkEmploymentStatus(ScoringDataDto scoringData) {
        EmploymentDto employment = scoringData.getEmployment();

        switch (employment.getEmploymentStatus()) {
            case SELF_EMPLOYED -> finalRate.add(BigDecimal.valueOf(2));
            case BUSINESS_OWNER -> finalRate.add(BigDecimal.valueOf(1));
            case UNEMPLOYED -> throw new IllegalArgumentException("Employment status is unemployed");
        }

    }


}
