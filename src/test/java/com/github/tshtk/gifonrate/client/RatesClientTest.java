package com.github.tshtk.gifonrate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tshtk.gifonrate.model.GifObject;
import com.github.tshtk.gifonrate.model.HistoricalRates;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RatesClientTest {

    @Test
    void shouldParse() throws IOException {
        String path = "rates-response.json";
        Double expectedRate = 66.250044;

        String json = Files.readString(Path.of(new ClassPathResource(path).getURI()));
        ObjectMapper mapper = new ObjectMapper();
        HistoricalRates historicalRates = mapper.readValue(json, HistoricalRates.class);
        Double rate = historicalRates.getRates().get("RUB");
        assertEquals(expectedRate, rate);
    }
}
