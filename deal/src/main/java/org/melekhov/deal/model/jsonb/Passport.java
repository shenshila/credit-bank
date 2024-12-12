package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.UUID;

@Embeddable
public class Passport {
    private UUID passport;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
