package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    private UUID passport;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
