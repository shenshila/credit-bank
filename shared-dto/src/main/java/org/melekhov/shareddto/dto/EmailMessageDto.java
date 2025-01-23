package org.melekhov.shareddto.dto;

import lombok.*;
import org.melekhov.shareddto.enums.Theme;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailMessageDto {
    private String address;
    private Theme theme;
    private UUID statementId;
    private String text;
}