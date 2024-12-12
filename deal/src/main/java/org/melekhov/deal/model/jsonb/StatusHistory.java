package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;
import org.melekhov.deal.model.enums.ChangeType;

import java.time.LocalDateTime;

@Embeddable
public class StatusHistory {
    private String status;
    private LocalDateTime time;
    private ChangeType changeType;
}
