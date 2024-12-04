package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class PaymentScheduleElementDto {

    @NotNull(message = "Number cannot be null")
    @Min(value = 1, message = "Number must be at least 1")
    @Schema(description = "Номер платежа", example = "5")
    private Integer number;

    @NotNull(message = "Date cannot be null")
    @Future(message = "Date must be in the future")
    @Schema(description = "Дата платежа", example = "25.02.2020")
    private LocalDate date;

    @NotNull(message = "TotalPayment cannot be null")
    @Positive
    @Schema(description = "Сумма платежа", example = "12234.55")
    private BigDecimal totalPayment;

    @NotNull(message = "InterestPayment cannot be null")
    @Positive
    @Schema(description = "Выплата процентов", example = "5678.55")
    private BigDecimal interestPayment;

    @NotNull(message = "DebtPayment cannot be null")
    @Positive
    @Schema(description = "Выплата долга", example = "7456.23")
    private BigDecimal debtPayment;

    @NotNull(message = "RemainingDebt cannot be null")
    @Positive
    @Schema(description = "Осталось выплатить", example = "324345.11")
    private BigDecimal remainingDebt;

}
