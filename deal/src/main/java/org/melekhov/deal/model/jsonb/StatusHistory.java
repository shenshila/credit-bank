package org.melekhov.deal.model.jsonb;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.melekhov.deal.model.enums.ChangeType;

import java.time.LocalDateTime;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {
    private String statusType;
    private LocalDateTime time;
    private ChangeType changeType;
}
