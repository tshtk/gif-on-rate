package com.github.tshtk.gifonrate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tshtk.gifonrate.model.GifObject;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GifClientTest {

    @Test
    void shouldParse() throws IOException {
        String path = "gif-response.json";
        String expectedUrl = "https://media0.giphy.com/media/l3q2Z2QUNmQDQn9tK/giphy.gif?cid=75b62480a00ead824da11cb32cf8dd4d1a4db9761a6fa2ce&rid=giphy.gif&ct=g";

        String json = Files.readString(Path.of(new ClassPathResource(path).getURI()));
        ObjectMapper mapper = new ObjectMapper();
        GifObject gifObject = mapper.readValue(json, GifObject.class);
        String url = gifObject.getUrl();
        assertEquals(expectedUrl, url);
    }
}
