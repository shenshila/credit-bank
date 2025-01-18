//package org.melekhov.statement.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.melekhov.shareddto.dto.LoanOfferDto;
//import org.melekhov.shareddto.dto.LoanStatementRequestDto;
//import org.melekhov.statement.service.StatementService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(StatementController.class)
//class StatementControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockBean
//    StatementService statementService;
//
//    LoanOfferDto loanOfferDto;
//
//    LoanStatementRequestDto loanStatementRequestDto;
//
//    @BeforeEach
//    void setUp() {
//        loanOfferDto = new LoanOfferDto();
//        loanOfferDto.setStatementId(UUID.randomUUID());
//        loanOfferDto.setRequestedAmount(new BigDecimal("100000.00"));
//        loanOfferDto.setTotalAmount(new BigDecimal("110000.00"));
//        loanOfferDto.setTerm(15);
//        loanOfferDto.setMonthlyPayment(new BigDecimal("7884.60"));
//        loanOfferDto.setRate(new BigDecimal("11"));
//        loanOfferDto.setIsInsuranceEnabled(true);
//        loanOfferDto.setIsSalaryClient(true);
//
//        loanStatementRequestDto = new LoanStatementRequestDto();
//        loanStatementRequestDto.setAmount(new BigDecimal("100000.00"));
//        loanStatementRequestDto.setTerm(15);
//        loanStatementRequestDto.setFirstName("John");
//        loanStatementRequestDto.setLastName("Doe");
//        loanStatementRequestDto.setMiddleName("Michael");
//        loanStatementRequestDto.setEmail("john.doe@example.com");
//        loanStatementRequestDto.setBirthDate(LocalDate.now().minusYears(30));
//        loanStatementRequestDto.setPassportSeries("1234");
//        loanStatementRequestDto.setPassportNumber("567890");
//    }
//
//    @Test
//    void statement() throws Exception {
//        when(statementService.generateLoanOffers(any(LoanStatementRequestDto.class))).thenReturn(Arrays.asList(loanOfferDto));
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/statement")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loanStatementRequestDto)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testSelectOffer() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/statement/offer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectToJson(loanOfferDto)))
//                .andExpect(status().isOk());
//    }
//
//    private String objectToJson(Object obj) throws Exception {
//        return new ObjectMapper().writeValueAsString(obj);
//    }
//}