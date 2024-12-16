package org.melekhov.deal.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.melekhov.deal.dto.*;
import org.melekhov.deal.feign.FeignCalculatorService;
import org.melekhov.deal.mapper.ClientMapper;
import org.melekhov.deal.mapper.ScoringMapper;
import org.melekhov.deal.mapper.StatementMapper;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.deal.model.enums.ChangeType;
import org.melekhov.deal.model.jsonb.StatusHistory;
import org.melekhov.deal.repository.ClientRepository;
import org.melekhov.deal.repository.StatementRepository;
import org.melekhov.deal.service.DealService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientMapper clientMapper;
    private final StatementMapper statementMapper;
    private final ScoringMapper scoringMapper;
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
    public void selectOffer(LoanOfferDto request) {
        Statement statement = statementRepository.getReferenceById(request.getStatementId());
        updateStatement(statement, request);
    }

    public void updateStatement(Statement statement, LoanOfferDto request) {
        StatusHistory statusHistory = StatusHistory.builder()
                .statusType(ApplicationStatus.CC_APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();

        List<StatusHistory> list = statement.getStatusHistory();
        list.add(statusHistory);
        statement.setStatusHistory(list);

        statement.setStatus(statusHistory.getStatusType());
        statement.setAppliedOffer(request);
        statementRepository.save(statement);
    }

    private void setStatementId(List<LoanOfferDto> offers, UUID statementId) {
        for (LoanOfferDto offer : offers) {
            offer.setStatementId(statementId);
        }
    }

    @Override
    public void calculateCredit(UUID statementId, FinishRegistrationRequestDto request) {
        Statement statement = statementRepository.getReferenceById(statementId);
        ScoringDataDto scoringData = scoringMapper.mapToScoring(request, statement);

        System.out.println("amount: " + scoringData.getAmount());
        System.out.println("term: " + scoringData.getTerm());
        System.out.println("firstName: " + scoringData.getFirstName());
        System.out.println("lastName: " + scoringData.getLastName());
        System.out.println("middleName: " + scoringData.getMiddleName());
        System.out.println("gender: " + scoringData.getGender());
        System.out.println("birthDate: " + scoringData.getBirthDate());
        System.out.println("passportSeries: " + scoringData.getPassportSeries());
        System.out.println("passportNumber: " + scoringData.getPassportNumber());
        System.out.println("passportIssueDate: " + scoringData.getPassportIssueDate());
        System.out.println("passportIssueBranch: " + scoringData.getPassportIssueBranch());
        System.out.println("maritalStatus: " + scoringData.getMaritalStatus());
        System.out.println("dependentAmount: " + scoringData.getDependentAmount());
        System.out.println("employmentStatus: " + scoringData.getEmployment().getEmploymentStatus());
        System.out.println("INN: " + scoringData.getEmployment().getEmployerINN());
        System.out.println("position: " + scoringData.getEmployment().getPosition());
        System.out.println("salary: " + scoringData.getEmployment().getSalary());
        System.out.println("workExperienceTotal: " + scoringData.getEmployment().getWorkExperienceTotal());
        System.out.println("workExperienceCurrent: " + scoringData.getEmployment().getWorkExperienceCurrent());
        System.out.println("accountNumber: " + scoringData.getAccountNumber());
        System.out.println("isInsuranceEnabled: " + scoringData.getIsInsuranceEnabled());
        System.out.println("isSalaryClient: " + scoringData.getIsSalaryClient());
        CreditDto credit = feignCalculatorService.calculateCredit(scoringData);
    }
}
