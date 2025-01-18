package org.melekhov.deal.dto;

import lombok.Builder;
import lombok.Data;
import org.melekhov.deal.model.enums.Theme;

import java.util.UUID;

@Data
@Builder
public class EmailMessageDto {

    private String address;
    private Theme theme;
    private UUID statementId;
    private String text;

}
