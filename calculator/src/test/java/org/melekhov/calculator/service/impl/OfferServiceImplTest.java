package org.melekhov.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.LoanOfferDto;
import org.melekhov.calculator.dto.LoanStatementRequestDto;
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

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @Mock
    private LoanCalculator loanCalculator;

    @Mock
    LoanOfferDto loanOfferDto;

    @InjectMocks
    private OfferServiceImpl offerService;
    LoanStatementRequestDto loanStatement = new LoanStatementRequestDto();


    @BeforeEach
    void setUp() {
        loanStatement.setAmount(new BigDecimal("100000"));
        loanStatement.setTerm(12);
        loanStatement.setFirstName("John");
        loanStatement.setLastName("Doe");
        loanStatement.setMiddleName("Mss");
        loanStatement.setEmail("john.doe@example.com");
        loanStatement.setBirthDate(LocalDate.parse("1990-01-01"));
        loanStatement.setPassportSeries("1234");
        loanStatement.setPassportNumber("123456");

        ReflectionTestUtils.setField(offerService, "BASE_RATE", new BigDecimal("15"));
    }

    @Test
    void calcLoanOffers() {
        ResponseEntity<List<LoanOfferDto>> result = offerService.getOffers(loanStatement);
        assertEquals(4, result.getBody().size());

        List<BigDecimal> rates = List.of(
                new BigDecimal("11"),
                new BigDecimal("12"),
                new BigDecimal("14"),
                new BigDecimal("15")
        );

        for (int i = 0; i < result.getBody().size(); i++) {
            assertEquals(rates.get(i), result.getBody().get(i).getRate());
        }

    }

}

