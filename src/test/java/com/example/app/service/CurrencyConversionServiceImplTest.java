package com.example.app.service;

import com.example.app.entity.Currency;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class CurrencyConversionServiceImplTest {

    @Autowired
    CurrencyConversionService currencyConversionService;

    @Test
    void convert() throws ParseException {
        BigDecimal inr = currencyConversionService.convert(Currency.USD, Currency.INR, BigDecimal.valueOf(1L));
        System.out.println("Converted 1 USD to INR = " + inr);
    }
}