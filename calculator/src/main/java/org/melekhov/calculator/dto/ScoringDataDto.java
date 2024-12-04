package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class ScoringDataDto {

    @NotNull(message = "Amount cannot be null")
    @Min(value = 20000, message = "Amount must be at least 20000")
    @Schema(description = "Запрашиваемая сумма кредита", example = "656666.66")
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    @Min(value = 6, message = "Term must be at least 1")
    @Max(value = 360, message = "Term must be less than 360")
    @Schema(description = "Срок кредита(месяцев)", example = "19")
    private Integer term;

    @NotNull(message = "firstName cannot be null")
    @Size(min = 2, max = 30, message = "firstName must be between 2 and 30 characters")
    @Schema(description = "Имя клиента", example = "Иван")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @Size(min = 2, max = 30, message = "lastName must be between 2 and 30 characters")
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Size(min = 2, max = 30, message = "middleName must be less than 30 characters")
    @Schema(description = "Отчество", example = "Иваныч")
    private String middleName;

    @NotNull(message = "gender cannot be null")
    @Schema(description = "Пол", example = "MALE", allowableValues = {"MALE", "FEMALE", "UNKNOWN"})
    private Gender gender;

    @NotNull(message = "birthDate cannot be null")
    @Past(message = "birthDate must be in the past")
    @Schema(description = "Дата рождения", example = "22.02.2000")
    private LocalDate birthDate;

    @NotNull(message = "passportSeries cannot be null")
    @Pattern(regexp = "^\\d{4}$", message = "passportSeries must be 4 digits")
    @Schema(description = "Серия паспорта", example = "1234")
    private String passportSeries;

    @NotNull(message = "passportNumber cannot be null")
    @Pattern(regexp = "^\\d{6}$", message = "passportNumber must be 6 digits")
    @Schema(description = "Номер паспорта", example = "123456")
    private String passportNumber;

    @NotNull(message = "passportIssueDate cannot be null")
    @Past(message = "passportIssueDate must be in the past")
    @Schema(description = "Когда выдан", example = "2020-01-22")
    private LocalDate passportIssueDate;

    @NotNull(message = "passportIssueBranch cannot be null")
    @Schema(description = "Кем выдан", example = "ОВД Центрального района")
    private String passportIssueBranch;

    @NotNull(message = "maritalStatus cannot be null")
    @Schema(description = "Женат/Замужем", example = "SINGLE", allowableValues = {"SINGLE", "MARRIED", "DIVORCED"})
    private MaritalStatus maritalStatus;

    @NotNull(message = "dependentAmount cannot be null")
    @Positive
    @Schema(example = "234444.23")
    private Integer dependentAmount;

    @NotNull(message = "employment cannot be null")
    @Valid
    @Schema(description = "Информация о работнике")
    private EmploymentDto employment;

    @NotNull(message = "accountNumber cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "accountNumber must be 10 digits")
    @Schema(description = "Нормер", example = "1231231231")
    private String accountNumber;

    @NotNull(message = "isInsuranceEnabled cannot be null")
    @Schema(description = "Есть ли страховка", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be null")
    @Schema(description = "Является ли зарплатным клиентом", example = "false")
    private Boolean isSalaryClient;

}
