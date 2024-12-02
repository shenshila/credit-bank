package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanOfferDto {

    @NotNull(message = "statementId cannot be null")
    @Schema(description = "Идетификационый номер", example = "16763be4-6022-406e-a950-fcd5018633ca")
    private UUID statementId;

    @NotNull(message = "requestedAmount cannot be null")
    @DecimalMin(value = "20000.00", message = "requestedAmount must be greater than 20000")
    @Schema(description = "Запрашиваемая сумма", example = "450000.00")
    private BigDecimal requestedAmount;

    @NotNull(message = "totalAmount cannot be null")
    @Schema(description = "Итоговая сумма", example = "512567.67")
    private BigDecimal totalAmount;

    @NotNull(message = "term cannot be null")
    @Min(value = 6, message = "term must be at least 6")
    @Schema(description = "На сколько месяцев", example = "15")
    private Integer term;

    @NotNull(message = "monthlyPayment cannot be null")
    @DecimalMin(value = "0.01", message = "monthlyPayment must be greater than zero")
    @Schema(description = "Ежемесячный платеж", example = "15654.34")
    private BigDecimal monthlyPayment;

    @NotNull(message = "rate cannot be null")
    @DecimalMin(value = "0", message = "rate must be non-negative")
    @Max(value = 100, message = "rate cannot exceed 100%")
    @Schema(description = "Ставка", example = "12")
    private BigDecimal rate;

    @NotNull(message = "isInsuranceEnabled cannot be null")
    @Schema(description = "Есть ли страховка", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be null")
    @Schema(description = "Является зарплатным клиентом", example = "false")
    private Boolean isSalaryClient;

}
