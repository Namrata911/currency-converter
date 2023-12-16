package com.example.app.service;


import com.example.app.entity.Currency;
import com.example.app.exception.ServerErrorException;
import com.example.app.util.CurrencyRateRestClient;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    @Autowired
   private CurrencyRateRestClient currencyRateRestClient;

    @Override
    public BigDecimal convert(Currency source, Currency target, BigDecimal amount) throws ParseException {
        String rate = currencyRateRestClient.fetchRatesForCurrency(source, target);
        if(rate!=null){
            return convertForRate(rate,amount, target.getDecimalPlaces());
        }
        throw new ServerErrorException("Conversion Rate Not Defined for ");
    }

    private  BigDecimal convertForRate(String rate, BigDecimal amount, int precision) {
        // Convert rate and amount to BigDecimal
        BigDecimal rateBigDecimal = new BigDecimal(rate);

        // Perform the currency conversion calculation
        BigDecimal result = rateBigDecimal.multiply(amount);

        // Set the scale to 2 decimal places and rounding mode
    return  result.setScale(precision, RoundingMode.HALF_UP);
    }
}
