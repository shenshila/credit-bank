package org.melekhov.calculator.service;

import org.melekhov.calculator.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ScoringService {
    public BigDecimal calcRate(ScoringDataDto scoringData, BigDecimal rate);
}
