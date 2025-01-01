package org.melekhov.statement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.melekhov.statement.dto.LoanStatementRequestDto;
import org.melekhov.statement.service.PreScoringService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
public class PreScoringServiceImpl implements PreScoringService {

    @Override
    public void preScoring(LoanStatementRequestDto request) {
        log.info("Starting pre-scoring for request: {}", request);

        try {
            if (!isValidName(request.getFirstName()) || !isValidName(request.getLastName())) {
                throw new IllegalArgumentException("First name and last name must contain between 2 and 30 Latin letters");
            }
            if (request.getMiddleName() != null && !isValidName(request.getMiddleName())) {
                throw new IllegalArgumentException("Middle name, if provided, must contain between 2 and 30 Latin letters");
            }
            if (request.getAmount().compareTo(BigDecimal.valueOf(20000)) < 0) {
                throw new IllegalArgumentException("Loan amount must be greater than or equal to 20,000");
            }
            if (request.getTerm() < 6) {
                throw new IllegalArgumentException("Loan term must be greater than or equal to 6 months");
            }
            if (!isValidDateOfBirth(request.getBirthDate())) {
                throw new IllegalArgumentException("Age must be at least 18 years");
            }
            if (!isValidEmail(request.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (!isValidPassport(request.getPassportSeries(), request.getPassportNumber())) {
                throw new IllegalArgumentException("Invalid passport series or number format");
            }

            log.info("Pre-scoring successfully completed for request: {}", request);
        } catch (Exception e) {
            log.error("Error during pre-scoring process: {}", e.getMessage(), e);
            throw e;
        }
    }

    private boolean isValidName(String name) {
        boolean isValid = name != null && name.matches("^[a-zA-Z]{2,30}$");
        log.debug("Validating name '{}': {}", name, isValid ? "passed" : "failed");
        return isValid;
    }

    private boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        boolean isValid = dateOfBirth.isBefore(LocalDate.now().minusYears(18));
        log.debug("Validating date of birth '{}': {}", dateOfBirth, isValid ? "passed" : "failed");
        return isValid;
    }

    private boolean isValidEmail(String email) {
        boolean isValid = email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$");
        log.debug("Validating email '{}': {}", email, isValid ? "passed" : "failed");
        return isValid;
    }

    private boolean isValidPassport(String series, String number) {
        boolean isSeriesValid = series != null && series.matches("^\\d{4}$");
        boolean isNumberValid = number != null && number.matches("^\\d{6}$");
        log.debug("Validating passport series '{}' and number '{}': {}", series, number, (isSeriesValid && isNumberValid) ? "passed" : "failed");
        return isSeriesValid && isNumberValid;
    }
}