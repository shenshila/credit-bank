package org.melekhov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.melekhov.calculator.dto.enums.Theme;

@Data
public class EmailMessage {

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Schema(description = "email address", example = "cool.melekhov@gmail.com")
    private String address;

    @NotNull(message = "Theme cannot be null")
    @Schema(description = "Тема сообщения", example = "Проценты по кредиту")
    private Theme theme;

    @NotNull(message = "statementId cannot be null")
    @Schema(description = "Идентификатор сообщения", example = "16763be4-6022-406e-a950-fcd5018633ca")
    private Long statementId;

    @NotBlank(message = "Text cannot be blank")
    @Schema(description = "Сообщение", example = "Погасите задолженность по кредиту")
    private String text;
}
