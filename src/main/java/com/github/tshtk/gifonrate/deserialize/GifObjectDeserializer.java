package com.github.tshtk.gifonrate.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tshtk.gifonrate.model.GifObject;

import java.io.IOException;

public class GifObjectDeserializer extends JsonDeserializer<GifObject> {
    @Override
    public GifObject deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        JsonNode treeNode = parser.getCodec().readTree(parser);
        JsonNode data = treeNode.get("data");
        JsonNode images = data.get("images");
        JsonNode downsized = images.get("downsized");
        JsonNode url = downsized.get("url");
        return new GifObject(url.asText());
    }
}
