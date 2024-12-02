package org.melekhov.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.melekhov.calculator.dto.*;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;
import org.melekhov.calculator.dto.enums.Position;
import org.melekhov.calculator.service.CreditService;
import org.melekhov.calculator.service.OfferService;
import org.melekhov.calculator.service.impl.CreditServiceImpl;
import org.melekhov.calculator.service.impl.OfferServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CreditServiceImpl creditService;
    @Mock
    private OfferServiceImpl offerService;
    @InjectMocks
    private CalculatorController calculatorController;

    LoanStatementRequestDto loanStatement = new LoanStatementRequestDto();
    LoanOfferDto loanOfferDto = new LoanOfferDto();

    ScoringDataDto scoringData = new ScoringDataDto();
    CreditDto creditDto = new CreditDto();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        scoringData.setAmount(new BigDecimal(100000));
        scoringData.setTerm(12);
        scoringData.setFirstName("John");
        scoringData.setLastName("Doe");
        scoringData.setMiddleName("Mss");
        scoringData.setGender(Gender.MALE);
        scoringData.setBirthDate(LocalDate.now().minusYears(19));
        scoringData.setPassportSeries("1234");
        scoringData.setPassportNumber("123456");
        scoringData.setPassportIssueDate(LocalDate.now().minusMonths(1));
        scoringData.setPassportIssueBranch("МВД г. Москва");
        scoringData.setMaritalStatus(MaritalStatus.SINGLE);
        scoringData.setDependentAmount(0);
        EmploymentDto employment = new EmploymentDto();
        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employment.setEmployerINN("1234567890");
        employment.setSalary(new BigDecimal(50000));
        employment.setPosition(Position.EMPLOYEE);
        employment.setWorkExperienceTotal(5);
        employment.setWorkExperienceCurrent(2);
        scoringData.setEmployment(employment);
        scoringData.setAccountNumber("1234567890");
        scoringData.setIsInsuranceEnabled(true);
        scoringData.setIsSalaryClient(true);


        loanStatement.setAmount(new BigDecimal("100000"));
        loanStatement.setTerm(12);
        loanStatement.setFirstName("John");
        loanStatement.setLastName("Doe");
        loanStatement.setMiddleName("Mss");
        loanStatement.setEmail("john.doe@example.com");
        loanStatement.setBirthDate(LocalDate.parse("1990-01-01"));
        loanStatement.setPassportSeries("1234");
        loanStatement.setPassportNumber("123456");
    }

    @Test
    void calc() throws Exception {
        when(creditService.calc(any(ScoringDataDto.class)))
                .thenReturn(new ResponseEntity<>(creditDto, HttpStatus.OK));

        mockMvc.perform(post("/controller/calc")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scoringData)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(creditDto)));
    }

    @Test
    void offers() throws Exception {
        List<LoanOfferDto> loanOffers = Collections.singletonList(loanOfferDto);

        when(offerService.getOffers(any(LoanStatementRequestDto.class)))
                .thenReturn(new ResponseEntity<>(loanOffers, HttpStatus.OK));

        mockMvc.perform(post("/controller/offers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanStatement)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loanOffers)));

    }
    
}
