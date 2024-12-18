package org.melekhov.deal.mapper;

import org.melekhov.deal.dto.FinishRegistrationRequestDto;
import org.melekhov.deal.dto.ScoringDataDto;
import org.melekhov.deal.model.Statement;
import org.springframework.stereotype.Component;

@Component
public class ScoringMapper {

    public ScoringDataDto mapToScoring(FinishRegistrationRequestDto request, Statement statement) {

        ScoringDataDto scoringData = ScoringDataDto.builder()
                .gender(request.getGender())
                .maritalStatus(request.getMaritalStatus())
                .dependentAmount(request.getDependentAmount())
                .passportIssueDate(request.getPassportIssueDate())
                .passportIssueBranch(request.getPassportIssueBranch())
                .accountNumber(request.getAccountNumber())
                .firstName(statement.getClientId().getFirstName())
                .lastName(statement.getClientId().getLastName())
                .middleName(statement.getClientId().getMiddleName())
                .birthDate(statement.getClientId().getBirthDate())
                .passportNumber(statement.getClientId().getPassport().getNumber())
                .passportSeries(statement.getClientId().getPassport().getSeries())
                .employment(request.getEmployment())
                .amount(statement.getAppliedOffer().getRequestedAmount())
                .term(statement.getAppliedOffer().getTerm())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .build();

        return scoringData;

    }

}
