//package org.melekhov.statement.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.melekhov.shareddto.dto.LoanOfferDto;
//import org.melekhov.shareddto.dto.LoanStatementRequestDto;
//import org.melekhov.statement.feign.FeignDealControllerClient;
//import org.melekhov.statement.service.PreScoringService;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class StatementServiceImplTest {
//
//    @Mock
//    private PreScoringService preScoringService;
//
//    @Mock
//    private FeignDealControllerClient feignDealControllerClient;
//
//    @InjectMocks
//    private StatementServiceImpl statementService;
//
//    private LoanStatementRequestDto loanStatementRequestDto;
//    private LoanOfferDto loanOfferDto;
//
//    @BeforeEach
//    void setUp() {
//        loanStatementRequestDto = new LoanStatementRequestDto();
//        loanStatementRequestDto.setAmount(new BigDecimal("100000.00"));
//        loanStatementRequestDto.setTerm(12);
//        loanStatementRequestDto.setFirstName("John");
//        loanStatementRequestDto.setLastName("Doe");
//        loanStatementRequestDto.setMiddleName("Michael");
//        loanStatementRequestDto.setEmail("john.doe@example.com");
//        loanStatementRequestDto.setBirthDate(LocalDate.now().minusYears(30));
//        loanStatementRequestDto.setPassportSeries("1234");
//        loanStatementRequestDto.setPassportNumber("567890");
//
//        loanOfferDto = new LoanOfferDto();
//        loanOfferDto.setRequestedAmount(new BigDecimal("100000.00"));
//        loanOfferDto.setTotalAmount(new BigDecimal("110000.00"));
//        loanOfferDto.setTerm(12);
//        loanOfferDto.setMonthlyPayment(new BigDecimal("9166.67"));
//        loanOfferDto.setRate(new BigDecimal("10.0"));
//        loanOfferDto.setIsInsuranceEnabled(true);
//        loanOfferDto.setIsSalaryClient(true);
//    }
//
//    @Test
//    void generateLoanOffers_Success() {
//        when(feignDealControllerClient.calculateLoanOffer(any(LoanStatementRequestDto.class)))
//                .thenReturn(Collections.singletonList(loanOfferDto));
//
//        List<LoanOfferDto> result = statementService.generateLoanOffers(loanStatementRequestDto);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(loanOfferDto, result.get(0));
//
//        verify(preScoringService, times(1)).preScoring(loanStatementRequestDto);
//        verify(feignDealControllerClient, times(1)).calculateLoanOffer(loanStatementRequestDto);
//    }
//
//    @Test
//    void generateLoanOffers_PreScoringFails() {
//        doThrow(new IllegalArgumentException("Pre-scoring failed"))
//                .when(preScoringService).preScoring(any(LoanStatementRequestDto.class));
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                statementService.generateLoanOffers(loanStatementRequestDto));
//
//        assertEquals("Pre-scoring failed", exception.getMessage());
//
//        verify(preScoringService, times(1)).preScoring(loanStatementRequestDto);
//        verify(feignDealControllerClient, never()).calculateLoanOffer(any());
//    }
//
//    @Test
//    void selectOffer_Success() {
//        doNothing().when(feignDealControllerClient).selectLoanOffer(any(LoanOfferDto.class));
//
//        assertDoesNotThrow(() -> statementService.selectOffer(loanOfferDto));
//
//        verify(feignDealControllerClient, times(1)).selectLoanOffer(loanOfferDto);
//    }
//
//    @Test
//    void selectOffer_Failure() {
//        doThrow(new RuntimeException("Failed to select loan offer"))
//                .when(feignDealControllerClient).selectLoanOffer(any(LoanOfferDto.class));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () ->
//                statementService.selectOffer(loanOfferDto));
//
//        assertEquals("Failed to select loan offer", exception.getMessage());
//
//        verify(feignDealControllerClient, times(1)).selectLoanOffer(loanOfferDto);
//    }
//}
