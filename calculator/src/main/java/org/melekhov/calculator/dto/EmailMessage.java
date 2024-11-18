package org.melekhov.calculator.dto;

import lombok.Data;
import org.melekhov.calculator.dto.enums.Theme;

@Data
public class EmailMessage {
    String address;
    Theme theme;
    Long statementId;
    String text;
}
