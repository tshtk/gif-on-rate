package com.github.tshtk.gifonrate.controller;

import com.github.tshtk.gifonrate.client.GifClient;
import com.github.tshtk.gifonrate.client.RatesClient;
import com.github.tshtk.gifonrate.model.GifObject;
import com.github.tshtk.gifonrate.model.HistoricalRates;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GifOnRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatesClient ratesClient;

    @MockBean
    private GifClient gifClient;

    private final String path = "/gifs";
    private final String currency = "RUB";
    private final Map<String, Double> rates = Collections.singletonMap(currency, 70.0);
    private final byte[] gifFile = new byte[77];

    @Test
    void shouldReturnGifImage() throws Exception {
        when(ratesClient.getRates(anyString(),anyString(),anyString())).thenReturn(new HistoricalRates(rates));
        String gifUrl = "https://test.url/gif";
        when(gifClient.getGifObjectByTag(anyString(),anyString())).thenReturn(new GifObject(gifUrl));
        when(gifClient.getFile(URI.create(gifUrl))).thenReturn(gifFile);

        MvcResult result = this.mockMvc
            .perform(
                get(path)
               .param("currency", currency))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.IMAGE_GIF))
            .andReturn();

        assertThat(result.getResponse().getContentAsByteArray()).isEqualTo(gifFile);
    }

    @Test
    void shouldReturnBadRequestStatus() throws Exception {
        String wrongCurrency = "HHH";
        this.mockMvc
            .perform(
                get(path)
               .param("currency", wrongCurrency))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundStatus() throws Exception {
        when(ratesClient.getRates(anyString(),anyString(),anyString())).thenReturn(new HistoricalRates(rates));

        String noDataCurrency = "EUR";
        this.mockMvc
            .perform(
                get(path)
               .param("currency", noDataCurrency))
            .andExpect(status().isNotFound());
    }
}
