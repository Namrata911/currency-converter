package com.example.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OutputDTO {
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;

    private BigDecimal targetAmount;

}
