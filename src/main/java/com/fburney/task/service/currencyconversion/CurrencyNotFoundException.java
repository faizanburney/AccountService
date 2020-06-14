package com.fburney.task.service.currencyconversion;

public class CurrencyNotFoundException extends IllegalArgumentException {
    public CurrencyNotFoundException(String s) {
        super(s);
    }
}
