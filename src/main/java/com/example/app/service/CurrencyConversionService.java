package com.example.app.service;

import com.example.app.entity.Currency;
import org.json.simple.parser.ParseException;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    BigDecimal convert(Currency source, Currency target, BigDecimal amount) throws ParseException;

}
