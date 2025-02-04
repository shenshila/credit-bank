package org.melekhov.statement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatementController.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatementService statementService;

    private LoanOfferDto loanOfferDto;
    private LoanStatementRequestDto loanStatementRequestDto;

    @BeforeEach
    void setUp() {
        // Создаем экземпляр LoanOfferDto с использованием конструктора
        loanOfferDto = new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("100000.00"),
                new BigDecimal("110000.00"),
                15,
                new BigDecimal("7884.60"),
                new BigDecimal("11"),
                true,
                true
        );

        // Создаем экземпляр LoanStatementRequestDto с использованием конструктора
        loanStatementRequestDto = new LoanStatementRequestDto(
                new BigDecimal("100000.00"),
                15,
                "John",
                "Doe",
                "Michael",
                "john.doe@example.com",
                LocalDate.now().minusYears(30),
                "1234",
                "567890"
        );
    }

    @Test
    void shouldGenerateLoanOffers() throws Exception {
        when(statementService.generateLoanOffers(any(LoanStatementRequestDto.class)))
                .thenReturn(Collections.singletonList(loanOfferDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanStatementRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(statementService, times(1)).generateLoanOffers(any(LoanStatementRequestDto.class));
    }

    @Test
    void shouldSelectLoanOffer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanOfferDto)))
                .andExpect(status().isOk());

        verify(statementService, times(1)).selectOffer(any(LoanOfferDto.class));
    }
}
