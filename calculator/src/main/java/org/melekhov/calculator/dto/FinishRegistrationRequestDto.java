package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.melekhov.calculator.dto.enums.Gender;
import org.melekhov.calculator.dto.enums.MaritalStatus;

import java.time.LocalDate;

@Data
public class FinishRegistrationRequestDto {

    @NotNull(message = "Gender cannot be null")
    @Schema(description = "Пол", example = "MALE", allowableValues = {"MALE", "FEMALE", "UNKNOWN"})
    private Gender gender;

    @NotNull(message = "MaritalStatus cannot be null")
    @Schema(description = "Женат/Замужем", example = "SINGLE", allowableValues = {"SINGLE", "MARRIED"})
    private MaritalStatus maritalStatus;

    @NotNull(message = "dependentAmount cannot be null")
    @Min(value = 0, message = "dependentAmount must be non-negative")
    @Schema(example = "2")
    private Integer dependentAmount;

    @NotNull(message = "passportIssueDate cannot be null")
    @Past(message = "passportIssueDate must be in the past")
    @Schema(description = "Дата выдачи паспорта", example = "2024-11-29")
    private LocalDate passportIssueDate;

    @NotNull(message = "passportIssueBranch cannot be null")
    @Schema(description = "Кем выдан паспорт", example = "ОВД Центрального района")
    private String passportIssueBranch;

    @NotNull(message = "employment cannot be null")
    @Valid
    @Schema(description = "Информация о работнике")
    private EmploymentDto employment;

    @NotNull(message = "accountNumber cannot be null")
    @Pattern(regexp = "^\\d+$", message = "accountNumber must contain only digits")
    @Schema(description = "Номер", example = "123455678")
    private String accountNumber;

}
