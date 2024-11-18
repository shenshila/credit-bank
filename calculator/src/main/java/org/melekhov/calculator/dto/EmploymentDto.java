package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Position;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    EmploymentStatus employmentStatus;
    String employerINN;
    BigDecimal salary;
    Position position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
