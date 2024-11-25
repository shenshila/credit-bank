package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.ChangeType;
import org.melekhov.calculator.dto.enums.Status;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {
    private Status status;
    private LocalDateTime time;
    private ChangeType changeType;
}
