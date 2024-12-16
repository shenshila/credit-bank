package org.melekhov.deal.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.melekhov.deal.dto.FinishRegistrationRequestDto;
import org.melekhov.deal.dto.LoanOfferDto;
import org.melekhov.deal.dto.LoanStatementRequestDto;
import org.melekhov.deal.feign.FeignCalculatorService;
import org.melekhov.deal.mapper.ClientMapper;
import org.melekhov.deal.mapper.StatementMapper;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.repository.ClientRepository;
import org.melekhov.deal.repository.StatementRepository;
import org.melekhov.deal.service.DealService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientMapper clientMapper;
    private final StatementMapper statementMapper;
    private final ClientRepository clientRepository;
    private final FeignCalculatorService feignCalculatorService;
    private final StatementRepository statementRepository;

    @Override
    public List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto request) {
        Client client = clientMapper.mapToClient(request);
        client = clientRepository.save(client);

        Statement statement = statementMapper.mapToStatement(request, client);

        System.out.println("statementId: " + statement.getStatementId());
        System.out.println("clientId: " + statement.getClientId());
        System.out.println("creditId: " + statement.getCreditId());
        System.out.println("status: " + statement.getStatus());
        System.out.println("createdOn: " + statement.getCreatedOn());
        System.out.println("appliedOffer: " + statement.getAppliedOffer());
        System.out.println("signDate: " + statement.getSignDate());
        System.out.println("sesCode: " + statement.getSesCode());
        System.out.println("statusHistory: " + statement.getStatusHistory());

        statement = statementRepository.save(statement);

        List<LoanOfferDto> offers = feignCalculatorService.getLoanOffers(request);
        setStatementId(offers, statement.getStatementId());

        return offers;
    }

    @Override
    public void selectOffer(LoanOfferDto offer) {

    }

    private void setStatementId(List<LoanOfferDto> offers, UUID statementId) {
        for (LoanOfferDto offer : offers) {
            offer.setStatementId(statementId);
        }
    }



    @Override
    public void calculateCredit(String loanId, FinishRegistrationRequestDto request) {

    }
}
