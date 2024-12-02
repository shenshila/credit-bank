package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class    LoanStatementRequestDto {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "20000", message = "Amount must be greater than 20000")
    @Schema(description = "Запрашиваемая сумма", example = "560400.67")
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    @Min(value = 6, message = "Term must be at least 6")
    @Max(value = 360, message = "Term cannot exceed 360 months")
    @Schema(description = "Срок кредита(месяцев)", example = "12")
    private Integer term;

    @NotNull(message = "firstName cannot be null")
    @Size(min = 2, max = 30, message = "firstName must be between 2 and 30 characters")
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @Size(min = 2, max = 30, message = "lastName must be between 2 and 30 characters")
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Size(max = 50, message = "middleName must be less than 50 characters")
    @Schema(description = "Отчество", example = "Иваныч")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Schema(description = "Адресс почтового ящика", example = "cool.melekhov@gmail.com")
    private String email;

    @NotNull(message = "birthDate cannot be null")
    @Past(message = "birthDate must be in the past")
    @Schema(description = "Дата рождения", example = "2024-11-29")
    private LocalDate birthDate;

    @NotNull(message = "passportSeries cannot be null")
    @Pattern(regexp = "^\\d{4}$", message = "passportSeries must be 4 digits")
    @Schema(description = "Серия пасспорта", example = "1234")
    private String passportSeries;

    @NotNull(message = "passportNumber cannot be null")
    @Pattern(regexp = "^\\d{6}$", message = "passportNumber must be between 6 digits")
    @Schema(description = "Номер пасспорта", example = "1234123412")
    private String passportNumber;

    public LoanStatementRequestDto() {};

    public LoanStatementRequestDto(BigDecimal bigDecimal, int i, String ivan, String ivanov, String ivanovich, String mail, LocalDate of, String number, String number1) {
    }
}
