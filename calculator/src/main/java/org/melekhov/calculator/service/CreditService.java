package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.ScoringDataDto;
import org.springframework.http.ResponseEntity;

public interface CreditService {
    ResponseEntity<CreditDto> calc(ScoringDataDto scoringData);
//    CreditDto calc(ScoringDataDto scoringData);
}
