package org.melekhov.deal.dto;

import lombok.Builder;
import lombok.Data;
import org.melekhov.deal.model.enums.Gender;
import org.melekhov.deal.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class ScoringDataDto {

    private BigDecimal amount;

    private Integer term;

    private String firstName;

    private String lastName;

    private String middleName;

    private Gender gender;

    private LocalDate birthDate;

    private String passportSeries;

    private String passportNumber;

    private LocalDate passportIssueDate;

    private String passportIssueBranch;

    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    private EmploymentDto employment;

    private String accountNumber;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;
}
