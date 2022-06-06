package com.github.tshtk.gifonrate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.github.tshtk.gifonrate.deserialize.GifObjectDeserializer;

@RequiredArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = GifObjectDeserializer.class)
public class GifObject {
    private final String url;
}
