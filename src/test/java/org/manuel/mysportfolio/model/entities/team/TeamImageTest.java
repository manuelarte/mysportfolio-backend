package org.manuel.mysportfolio.model.entities.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamImageTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testSerializeUrlImage() throws JsonProcessingException {
        final var teamImage = new UrlImage("https://localhost:8080");
        final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(teamImage));
        assertEquals("url", json.get("type"));
        assertEquals(teamImage.getUrl(), json.get("url"));
    }

    @Test
    public void testDeserializeUrlImage() throws IOException {
        final UrlImage expected = new UrlImage("https://localhost:8080");
        final var json = new JSONObject();
        json.put("type", "url");
        json.put("url", expected.getUrl());

        assertEquals(expected, OBJECT_MAPPER.readValue(json.toString(), TeamImage.class));
    }

    @Test
    public void testSerializeKitBasic() throws JsonProcessingException {
        final var teamImage = new KitBasicImage(123, 456);
        final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(teamImage));
        assertEquals("kit-basic", json.get("type"));
        assertEquals(teamImage.getShirtColour(), json.get("shirtColour"));
        assertEquals(teamImage.getPantsColour(), json.get("pantsColour"));
    }

    @Test
    public void testDeserializeKitBasic() throws IOException {
        final var expected = new KitBasicImage(123, 456);
        final var json = new JSONObject();
        json.put("type", "kit-basic");
        json.put("shirtColour", expected.getShirtColour());
        json.put("pantsColour", expected.getPantsColour());
        assertEquals(expected, OBJECT_MAPPER.readValue(json.toString(), TeamImage.class));
    }

}
