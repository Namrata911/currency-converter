package com.example.app.service.unit;

import com.example.app.entity.Currency;
import com.example.app.exception.ServerErrorException;
import com.example.app.service.CurrencyConversionService;
import com.example.app.service.CurrencyConversionServiceImpl;
import com.example.app.util.CurrencyRateRestClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyConversionServiceImplTest {
    private CurrencyRateRestClient rateRestClient;
    private CurrencyConversionService service;

    @BeforeEach
    void setup() throws ParseException {
        rateRestClient = mock(CurrencyRateRestClient.class);
        service = new CurrencyConversionServiceImpl(rateRestClient);
    }

    @Test
    void convertSameCurrencyTest() throws ParseException {
        when(rateRestClient.fetchRatesForCurrency(any(Currency.class), any(Currency.class))).thenReturn("1");
        BigDecimal convertedAmount = service.convert(Currency.INR, Currency.INR, new BigDecimal(1));
        assertEquals(new BigDecimal("1.00"), convertedAmount);
    }
    @Test
    @DisplayName("Test for rounding when converting INR to JPY")
    void convertINRtoJPYTest() throws ParseException {
        when(rateRestClient.fetchRatesForCurrency(eq(Currency.INR), eq(Currency.JPY))).thenReturn("1.7");
        BigDecimal convertedAmount = service.convert(Currency.INR, Currency.JPY, new BigDecimal(1));
        assertEquals(new BigDecimal("2"), convertedAmount);
    }

    @Test
    void convertServerExceptionTest() throws ParseException {
        doThrow(new ServerErrorException("API call failed")).when(rateRestClient).fetchRatesForCurrency(any(Currency.class), any(Currency.class));
        assertThrows(ServerErrorException.class, () -> service.convert(Currency.INR, Currency.INR, new BigDecimal(1)));
    }


}