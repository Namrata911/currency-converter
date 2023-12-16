package com.example.app.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CurrencyTest {

    @Test
    void getCurrencyForCode() {
        Arrays.stream(Currency.values()).forEach(
                cur -> assertEquals(cur, Currency.getCurrencyForCode(cur.getCode())));

    }

    @Test
    void getDecimalPlaces() {
        assertEquals(0, Currency.JPY.getDecimalPlaces());
        assertEquals(2, Currency.INR.getDecimalPlaces());
        assertEquals(2, Currency.USD.getDecimalPlaces());
        assertEquals(2, Currency.EUR.getDecimalPlaces());
    }

    @Test
    void getCode() {
        assertEquals("jpy", Currency.JPY.getCode());
        assertEquals("inr", Currency.INR.getCode());
        assertEquals("usd", Currency.USD.getCode());
        assertEquals("eur", Currency.EUR.getCode());
    }
}