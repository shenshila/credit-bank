package org.melekhov.shareddto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.melekhov.shareddto.enums.EmploymentStatus;
import org.melekhov.shareddto.enums.Position;

import java.math.BigDecimal;

@Builder
@Data
public class EmploymentDto {

    @NotNull(message = "employmentStatus cannot be null")
    @Schema(description = "Статус работника", example = "SELF_EMPLOYED", allowableValues = {
            "UNEMPLOYED", "EMPLOYED", "SELF_EMPLOYED", "BUSINESS_OWNER"
    })
    private EmploymentStatus employmentStatus;

    @Size(min = 10, max = 12, message = "employerINN must be 10-12 digits")
    @Pattern(regexp = "^\\d{10,12}$", message = "employerINN must contain only digits")
    @Schema(description = "ИНН сотрудника", example = "7701000000")
    private String employerINN;

    @NotNull(message = "salary cannot be null")
    @DecimalMin(value = "11000", message = "salary must be greater than 11000")
    @Schema(description = "Зарплата сотрудника", example = "73289.45")
    private BigDecimal salary;

    @NotNull(message = "position cannot be null")
    @Schema(description = "Должность сотрудника", example = "MANAGER", allowableValues = {
            "EMPLOYEE", "MANAGER", "TOP_MANAGER"
    })
    private Position position;

    @NotNull(message = "workExperienceTotal cannot be null")
    @Positive
    @Schema(description = "Рабочий стаж(месяцы)", example = "28")
    private Integer workExperienceTotal;

    @NotNull(message = "workExperienceCurrent cannot be null")
    @Positive
    @Schema(description = "Стаж работы на нынешней должности", example = "7")
    private Integer workExperienceCurrent;

}

