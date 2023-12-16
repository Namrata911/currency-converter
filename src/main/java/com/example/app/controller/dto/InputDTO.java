package com.example.app.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class InputDTO {
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;

}
