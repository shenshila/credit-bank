package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.ChangeType;
import org.melekhov.calculator.dto.enums.Status;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {
    Status status;
    LocalDateTime time;
    ChangeType changeType;
}
