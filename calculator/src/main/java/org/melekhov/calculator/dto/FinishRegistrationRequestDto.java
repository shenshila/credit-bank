package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;

import java.time.LocalDate;

@Data
public class FinishRegistrationRequestDto {
    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    EmploymentDto employment;
    String accountNumber;
}
