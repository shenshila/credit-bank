package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class CreditDto {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "20000", message = "Amount must be greater than 20000")
    @DecimalMax(value = "1000000")
    @Schema(description = "Полная сумма кредита", example = "456253.27")
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    @Min(value = 6, message = "Term must be at least 6")
    @Max(value = 360, message = "Term cannot exceed 360 months")
    @Schema(description = "Срок кредита(месяцы)", example = "15")
    private Integer term;

    @NotNull(message = "MonthlyPayment cannot be null")
    @Positive
    @Schema(description = "Ежесячный платеж", example = "13223.67")
    private BigDecimal monthlyPayment;

    @NotNull(message = "Rate cannot be null")
    @Positive
    @Max(value = 100, message = "Rate cannot exceed 100%")
    @Schema(description = "Ключевая ставка", example = "12")
    private BigDecimal rate;

    @NotNull(message = "Psk cannot be null")
    @Positive
    @Schema(description = "Расходы клиента", example = "78993.23")
    private BigDecimal psk;

    @NotNull(message = "isInsuranceEnabled cannot be null")
    @Schema(description = "Включена ли страховка", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be null")
    @Schema(description = "Является ли зарплатным клиентом", example = "false")
    private Boolean isSalaryClient;

    @NotNull(message = "paymentSchedule cannot be null")
    @Size(min = 6, message = "paymentSchedule must contain at least six elements")
    @Schema(description = "График платежей")
    private List<@Valid PaymentScheduleElementDto> paymentSchedule;

}
