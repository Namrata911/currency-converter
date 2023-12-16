package com.example.app.entity;

/**
 * Currently active currencies.
 */
public enum Currency {
    JPY("jpy",0),
    INR("inr",2),
    USD("usd",2),
    EUR("eur",2);
    private final String code;
    private final int decimalPlaces;

     Currency(String code, int decimalPlaces ) {
        this.code = code;
        this.decimalPlaces = decimalPlaces;
    }

    public static Currency getCurrencyForCode(String code) {
        return Currency.valueOf(code.toUpperCase());
    }
    public  String getCode() {
        return code;
    }


    public int getDecimalPlaces() {
        return decimalPlaces;
    }
}
