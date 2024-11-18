package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.CreditDto;
import org.melekhov.calculator.dto.ScoringDataDto;

public interface CalculatorService {
    CreditDto calc(ScoringDataDto scoringData);
}
