package org.melekhov.shareddto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.melekhov.shareddto.enums.Gender;
import org.melekhov.shareddto.enums.MaritalStatus;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishRegistrationRequestDto {

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    private LocalDate passportIssueDate;

    private String passportIssueBranch;

    private EmploymentDto employment;

    private String accountNumber;

}