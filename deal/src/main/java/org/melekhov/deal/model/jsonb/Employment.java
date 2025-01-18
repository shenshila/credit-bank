package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.melekhov.deal.model.enums.EmploymentPosition;
import org.melekhov.shareddto.enums.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employment {
    private UUID employment;
    private EmploymentStatus status;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
