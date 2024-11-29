package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.melekhov.calculator.dto.enums.ChangeType;
import org.melekhov.calculator.dto.enums.Status;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {
    @NotNull
    @Schema(description = "Статус выписки")
    private Status status;
    @NotNull
    @Schema(description = "Время выписки", example = "29.02.2020 13:45")
    private LocalDateTime time;
    @NotNull
    @Schema(description = "Изменить статус")
    private ChangeType changeType;
}
