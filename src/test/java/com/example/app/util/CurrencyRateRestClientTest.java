package com.example.app.util;

import com.example.app.entity.Currency;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyRateRestClientTest {
    @Autowired
    CurrencyRateRestClient client;

    @Test
    void test1() throws ParseException {
        String rate = client.fetchRatesForCurrency(Currency.USD, Currency.INR);
        System.out.println(rate);
    }

}