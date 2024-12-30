package org.melekhov.deal.dto;

import lombok.Builder;
import lombok.Data;
import org.melekhov.deal.model.enums.EmploymentPosition;
import org.melekhov.deal.model.enums.EmploymentStatus;

import java.math.BigDecimal;

@Builder
@Data
public class EmploymentDto {

    private EmploymentStatus employmentStatus;

    private String employerINN;

    private BigDecimal salary;

    private EmploymentPosition position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;
}
