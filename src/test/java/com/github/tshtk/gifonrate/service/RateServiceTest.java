package com.github.tshtk.gifonrate.service;

import com.github.tshtk.gifonrate.client.RatesClient;
import com.github.tshtk.gifonrate.exception.NoCurrencyDataException;
import com.github.tshtk.gifonrate.model.HistoricalRates;
import com.github.tshtk.gifonrate.model.RateChange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RateServiceTest {

    @MockBean
    private RatesClient ratesClient;

    @Autowired
    private RateService rateService;

    private final String currency = "RUB";
    private final String noDataCurrency = "EUR";
    private final Map<String, Double> rates = Collections.singletonMap(currency, 70.0);
    private final Map<String, Double> increaseRates = Collections.singletonMap(currency, 75.0);
    private final Map<String, Double> decreaseRates = Collections.singletonMap(currency, 65.0);
    private final String today = LocalDate.now(ZoneOffset.UTC).toString();
    private final String yesterday = LocalDate.now(ZoneOffset.UTC).minusDays(1).toString();

    @Test
    void shouldReturnIncrease() {
        when(ratesClient.getRates(anyString(), anyString(), eq(today))).thenReturn(new HistoricalRates(increaseRates));
        when(ratesClient.getRates(anyString(), anyString(), eq(yesterday))).thenReturn(new HistoricalRates(rates));

        assertThat(rateService.getChangeLastDay(currency)).isEqualTo(RateChange.INCREASE);
    }

    @Test
    void shouldReturnDecrease() {
        when(ratesClient.getRates(anyString(), anyString(), eq(today))).thenReturn(new HistoricalRates(decreaseRates));
        when(ratesClient.getRates(anyString(), anyString(), eq(yesterday))).thenReturn(new HistoricalRates(rates));

        assertThat(rateService.getChangeLastDay(currency)).isEqualTo(RateChange.DECREASE);
    }

    @Test
    void shouldReturnConst() {
        when(ratesClient.getRates(anyString(), anyString(), eq(today))).thenReturn(new HistoricalRates(rates));
        when(ratesClient.getRates(anyString(), anyString(), eq(yesterday))).thenReturn(new HistoricalRates(rates));


        assertThat(rateService.getChangeLastDay(currency)).isEqualTo(RateChange.CONST);
    }

    @Test
    void shouldThrowException() {
        when(ratesClient.getRates(anyString(), anyString(), eq(today))).thenReturn(new HistoricalRates(rates));
        when(ratesClient.getRates(anyString(), anyString(), eq(yesterday))).thenReturn(new HistoricalRates(rates));

        assertThatExceptionOfType(NoCurrencyDataException.class)
            .isThrownBy(() -> rateService.getChangeLastDay(noDataCurrency));
    }
}
