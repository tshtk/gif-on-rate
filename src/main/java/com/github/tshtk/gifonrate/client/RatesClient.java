package com.github.tshtk.gifonrate.client;

import com.github.tshtk.gifonrate.model.HistoricalRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rates", url = "${gifonrate.rates-client.url}")
public interface RatesClient {

    @GetMapping("{date}.json")
    HistoricalRates getRates(@RequestParam(value = "app_id") String appID,
                             @RequestParam(value = "base") String baseCurrency,
                             @PathVariable(value = "date") String date);
}
