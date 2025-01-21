package org.melekhov.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.dto.CreditDto;
import org.melekhov.deal.feign.FeignCalculatorService;
import org.melekhov.deal.mapper.ClientMapper;
import org.melekhov.deal.mapper.CreditMapper;
import org.melekhov.deal.mapper.ScoringMapper;
import org.melekhov.deal.mapper.StatementMapper;
import org.melekhov.deal.model.Client;
import org.melekhov.deal.model.Credit;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.deal.model.enums.ChangeType;
import org.melekhov.deal.model.jsonb.Employment;
import org.melekhov.deal.model.jsonb.Passport;
import org.melekhov.deal.model.jsonb.StatusHistory;
import org.melekhov.deal.repository.ClientRepository;
import org.melekhov.deal.repository.CreditRepository;
import org.melekhov.deal.repository.StatementRepository;
import org.melekhov.deal.service.DealService;
import org.melekhov.deal.service.KafkaDocumentService;
import org.melekhov.shareddto.dto.FinishRegistrationRequestDto;
import org.melekhov.shareddto.dto.LoanOfferDto;
import org.melekhov.shareddto.dto.LoanStatementRequestDto;
import org.melekhov.shareddto.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final ClientMapper clientMapper;
    private final StatementMapper statementMapper;
    private final ScoringMapper scoringMapper;
    private final CreditMapper creditMapper;

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;

    private final FeignCalculatorService feignCalculatorService;
    private final KafkaDocumentService kafkaDocumentService;

    @Override
    public List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto request) {
        log.info("Getting loan offers for request: {}", request);
        Client client = clientMapper.mapToClient(request);
        client = clientRepository.save(client);

        Statement statement = statementMapper.mapToStatement(client);
        statement = statementRepository.save(statement);

        List<LoanOfferDto> offers = feignCalculatorService.getLoanOffers(request);
        setStatementId(offers, statement.getStatementId());

        log.info("Returning loan offers: {}", offers);
        return offers;
    }

    @Override
    public void selectOffer(LoanOfferDto request) {
        log.info("Selecting loan offer: {}", request);
        Statement statement = statementRepository.getReferenceById(request.getStatementId());
        updateStatement(statement, request);
        log.info("Loan offer selected.");
    }

    public void updateStatement(Statement statement, LoanOfferDto request) {
        StatusHistory statusHistory = StatusHistory.builder()
                .statusType(ApplicationStatus.CC_APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();


        List<StatusHistory> list = statement.getStatusHistory();
        list.add(statusHistory);

        statement.setStatus(statusHistory.getStatusType());
        statement.setAppliedOffer(request);
        statementRepository.save(statement);
    }

    public void updateStatement(Statement statement, Credit credit) {
        StatusHistory statusHistory = StatusHistory.builder()
                .statusType(ApplicationStatus.APPROVAL)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();

        List<StatusHistory> list = statement.getStatusHistory();
        list.add(statusHistory);
        statement.setStatusHistory(list);
        statement.setCreditId(credit);

        statementRepository.save(statement);
    }

    private void setStatementId(List<LoanOfferDto> offers, UUID statementId) {
        for (LoanOfferDto offer : offers) {
            offer.setStatementId(statementId);
        }
    }

    @Override
    public void calculateCredit(UUID statementId, FinishRegistrationRequestDto request) {
        log.info("Calculating loan for statementId: {} with request: {}", statementId, request);
        Statement statement = statementRepository.getReferenceById(statementId);
        ScoringDataDto scoringData = scoringMapper.mapToScoring(request, statement);
        log.info("Scoring data created: {}", scoringData);

        CreditDto creditDto = feignCalculatorService.calculateCredit(scoringData);
        log.info("Credit data calculated: {}", creditDto);

        Credit credit = creditMapper.mapToCredit(creditDto);
        log.info("Credit created: {}", credit);
        creditRepository.save(credit);

        Client client = clientRepository.getReferenceById(statement.getClientId().getClientId());
        updateClientFromScoringData(client, scoringData);
        clientRepository.save(client);

        updateStatement(statement, credit);
        log.info("Loan calculated and credit created successfully.");
    }

    public Client updateClientFromScoringData(Client client, ScoringDataDto scoringData) {
        log.info("Updating existing client with scoring data: {}", scoringData);

        client.setAccountNumber(scoringData.getAccountNumber());
        client.setDependentAmount(scoringData.getDependentAmount());

        Employment employment = client.getEmployment() != null
                ? client.getEmployment()
                : new Employment();
        employment.setEmployment(client.getClientId());
        employment.setSalary(scoringData.getEmployment().getSalary());
        employment.setStatus(scoringData.getEmployment().getEmploymentStatus());
        employment.setPosition(scoringData.getEmployment().getPosition());
        employment.setEmployerINN(scoringData.getEmployment().getEmployerINN());
        employment.setWorkExperienceTotal(scoringData.getEmployment().getWorkExperienceTotal());
        employment.setWorkExperienceCurrent(scoringData.getEmployment().getWorkExperienceCurrent());
        client.setEmployment(employment);

        client.setGender(scoringData.getGender());
        client.setMaritalStatus(scoringData.getMaritalStatus());

        Passport passport = client.getPassport() != null
                ? client.getPassport()
                : new Passport();
        passport.setPassport(UUID.randomUUID());
        passport.setIssueBranch(scoringData.getPassportIssueBranch());
        passport.setIssueDate(scoringData.getPassportIssueDate());
        client.setPassport(passport);

        return client;
    }

}
