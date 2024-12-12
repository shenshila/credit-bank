package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;
import org.melekhov.deal.model.enums.EmploymentPosition;
import org.melekhov.deal.model.enums.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class Employment {
    private UUID employment;
    private EmploymentStatus status;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
