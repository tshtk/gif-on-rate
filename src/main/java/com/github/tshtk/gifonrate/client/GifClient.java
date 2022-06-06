package com.github.tshtk.gifonrate.client;

import com.github.tshtk.gifonrate.model.GifObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "gif-client", url = "${gifonrate.gif-client.url}")
public interface GifClient {

    @GetMapping
    GifObject getGifObjectByTag(@RequestParam(value = "api_key") String apiKey,
                                @RequestParam(value = "tag") String tag);

    @GetMapping
    byte[] getFile(URI url);
}
