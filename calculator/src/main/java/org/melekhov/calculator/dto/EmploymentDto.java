package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.EmploymentStatus;
import org.melekhov.calculator.dto.enums.Position;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
