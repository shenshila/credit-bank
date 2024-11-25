package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.Theme;

@Data
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long statementId;
    private String text;
}
