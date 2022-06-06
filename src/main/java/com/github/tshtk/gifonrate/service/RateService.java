package com.github.tshtk.gifonrate.service;

import com.github.tshtk.gifonrate.client.RatesClient;
import com.github.tshtk.gifonrate.exception.NoCurrencyDataException;
import com.github.tshtk.gifonrate.model.HistoricalRates;
import com.github.tshtk.gifonrate.model.RateChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Slf4j
@Service
public class RateService {
    private final RatesClient ratesClient;
    private final String appId;
    private final String baseCurrency;

    public RateService(final RatesClient ratesClient,
                       @Value("${gifonrate.rates-client.app-id}") final String appId,
                       @Value("${gifonrate.rates-client.base-currency}") final String baseCurrency) {
        this.ratesClient = ratesClient;
        this.appId = appId;
        this.baseCurrency = baseCurrency;
    }

    public RateChange getChangeLastDay(String currency) {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        Double todayRate = getRateOnDate(currency, today);
        Double yesterdayRate = getRateOnDate(currency, today.minusDays(1));
        int compare = Double.compare(todayRate, yesterdayRate);
        RateChange change = RateChange.byValue(compare);
        log.info("Rate {} to {} change for the last day: {}", currency, baseCurrency, change);
        return change;
    }

    private Double getRateOnDate(String currency, LocalDate date) {
        HistoricalRates rates = ratesClient.getRates(appId, baseCurrency, date.toString());
        if (!rates.getRates().containsKey(currency)) {
            log.info("No data for currency: {}", currency);
            throw new NoCurrencyDataException(currency);
        }
        Double rate = rates.getRates().get(currency);
        log.info("Rate {} to {} on {}: {}", currency, baseCurrency, date, rate);
        return rate;
    }
}
