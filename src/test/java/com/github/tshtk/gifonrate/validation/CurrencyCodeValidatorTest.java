package com.github.tshtk.gifonrate.validation;

import com.github.tshtk.gifonrate.exception.InvalidCurrencyCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyCodeValidatorTest {
    @Test
    void shouldValidate() {
        CurrencyCodeValidator validator = new CurrencyCodeValidator();

        assertDoesNotThrow(() -> validator.validate("RUB"));
        assertDoesNotThrow(() -> validator.validate("EUR"));
        assertDoesNotThrow(() -> validator.validate("USD"));

        assertThrows(InvalidCurrencyCodeException.class, () -> validator.validate("rub"));
        assertThrows(InvalidCurrencyCodeException.class, () -> validator.validate("125"));
        assertThrows(InvalidCurrencyCodeException.class, () -> validator.validate(""));
    }
}
