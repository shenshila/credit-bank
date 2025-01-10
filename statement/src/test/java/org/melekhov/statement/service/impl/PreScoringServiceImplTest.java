package org.melekhov.statement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.melekhov.statement.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreScoringServiceImplTest {

    private PreScoringServiceImpl preScoringService;
    private LoanStatementRequestDto request;

    @BeforeEach
    void setUp() {
        preScoringService = new PreScoringServiceImpl();
        request = new LoanStatementRequestDto();
        request.setAmount(new BigDecimal("50000"));
        request.setTerm(12);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setMiddleName("Michael");
        request.setEmail("john.doe@example.com");
        request.setBirthDate(LocalDate.now().minusYears(30));
        request.setPassportSeries("1234");
        request.setPassportNumber("567890");
    }

    @Test
    void preScoring_validRequest_shouldPass() {
        assertDoesNotThrow(() -> preScoringService.preScoring(request));
    }

    @Test
    void preScoring_invalidFirstName_shouldThrowException() {
        request.setFirstName("J");
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "First name and last name must contain between 2 and 30 Latin letters");
    }

    @Test
    void preScoring_invalidLastName_shouldThrowException() {
        request.setLastName("D!");
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "First name and last name must contain between 2 and 30 Latin letters");
    }

    @Test
    void preScoring_invalidAmount_shouldThrowException() {
        request.setAmount(new BigDecimal("10000"));
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Loan amount must be greater than or equal to 20,000");
    }

    @Test
    void preScoring_invalidTerm_shouldThrowException() {
        request.setTerm(5);
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Loan term must be greater than or equal to 6 months");
    }

    @Test
    void preScoring_invalidBirthDate_shouldThrowException() {
        request.setBirthDate(LocalDate.now().minusYears(17));
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Age must be at least 18 years");
    }

    @Test
    void preScoring_invalidEmail_shouldThrowException() {
        request.setEmail("invalid-email");
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Invalid email format");
    }

    @Test
    void preScoring_invalidPassportSeries_shouldThrowException() {
        request.setPassportSeries("12");
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Invalid passport series or number format");
    }

    @Test
    void preScoring_invalidPassportNumber_shouldThrowException() {
        request.setPassportNumber("5678");
        assertThrows(IllegalArgumentException.class, () -> preScoringService.preScoring(request),
                "Invalid passport series or number format");
    }
}
