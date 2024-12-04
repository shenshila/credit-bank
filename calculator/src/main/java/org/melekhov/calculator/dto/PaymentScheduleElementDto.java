package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentScheduleElementDto {

    @NotNull(message = "Number cannot be null")
    @Min(value = 1, message = "Number must be at least 1")
    @Schema(description = "Номер платежного месяца", example = "5")
    private Integer number;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    @Schema(description = "Дата платежа", example = "25.02.2020")
    private LocalDate date;

    @NotNull(message = "TotalPayment cannot be null")
    @DecimalMin(value = "0.01", message = "TotalPayment must be greater than zero")
    @Schema(description = "Сумма платежа", example = "12234.55")
    private BigDecimal totalPayment;

    @NotNull(message = "InterestPayment cannot be null")
    @DecimalMin(value = "0", message = "InterestPayment must be non-negative")
    @Schema(description = "Выплата процентов", example = "5678.55")
    private BigDecimal interestPayment;

    @NotNull(message = "DebtPayment cannot be null")
    @DecimalMin(value = "0", message = "DebtPayment must be non-negative")
    @Schema(description = "Выплата долга", example = "7456.23")
    private BigDecimal debtPayment;

    @NotNull(message = "RemainingDebt cannot be null")
    @DecimalMin(value = "0", message = "RemainingDebt must be non-negative")
    @Schema(description = "Осталось выплатить", example = "324345.11")
    private BigDecimal remainingDebt;

    public PaymentScheduleElementDto(Integer number,
                                     LocalDate date,
                                     BigDecimal totalPayment,
                                     BigDecimal interestPayment,
                                     BigDecimal debtPayment,
                                     BigDecimal remainingDebt) {
        this.number = number;
        this.date = date;
        this.totalPayment = totalPayment;
        this.interestPayment = interestPayment;
        this.debtPayment = debtPayment;
        this.remainingDebt = remainingDebt;
    }
}
