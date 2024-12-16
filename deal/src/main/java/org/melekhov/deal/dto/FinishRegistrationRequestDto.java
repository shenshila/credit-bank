package org.melekhov.deal.dto;

import lombok.Builder;
import lombok.Data;
import org.melekhov.deal.model.enums.Gender;
import org.melekhov.deal.model.enums.MaritalStatus;

import java.time.LocalDate;

@Builder
@Data
public class FinishRegistrationRequestDto {

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    private LocalDate passportIssueDate;

    private String passportIssueBranch;

    private EmploymentDto employment;

    private String accountNumber;

}
