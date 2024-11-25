package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;

import java.time.LocalDate;

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
