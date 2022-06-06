package com.github.tshtk.gifonrate.validation;

import com.github.tshtk.gifonrate.exception.InvalidCurrencyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Slf4j
@Service
public class CurrencyCodeValidator {
    public void validate(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            log.info("Invalid currency code: {}", currencyCode);
            throw new InvalidCurrencyCodeException(currencyCode);
        }
    }
}
