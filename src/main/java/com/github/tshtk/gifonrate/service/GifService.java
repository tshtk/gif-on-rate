package com.github.tshtk.gifonrate.service;

import com.github.tshtk.gifonrate.client.GifClient;
import com.github.tshtk.gifonrate.model.GifObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
public class GifService {
    private final GifClient gifClient;
    private final String apiKey;

    public GifService(GifClient gifClient,
                      @Value("${gifonrate.gif-client.api-key}") String apiKey) {
        this.gifClient = gifClient;
        this.apiKey = apiKey;
    }

    public byte[] getGifByTag(String tag) {
        GifObject gifObject = gifClient.getGifObjectByTag(apiKey, tag);
        URI uri = URI.create(gifObject.getUrl());
        log.info("Get gif url {} by tag \"{}\"", uri, tag);
        byte[] gif = gifClient.getFile(uri);
        log.info("Downloaded image size: {} bytes", gif.length);
        return gif;
    }
}
