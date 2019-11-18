package org.manuel.mysportfolio.model.entities.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamKitTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testSerializeKitBasic() throws JsonProcessingException {
        final TeamKit<PlainKitPart, PlainKitPart> teamKit = new TeamKit(new PlainKitPart(123), new PlainKitPart(123));
        final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(teamKit));
        assertEquals("plain", json.getJSONObject("shirt").getString("type"));
        assertEquals("plain", json.getJSONObject("pants").getString("type"));
        assertEquals(teamKit.getShirt().getColour(), json.getJSONObject("shirt").getInt("colour"));
        assertEquals(teamKit.getPants().getColour(), json.getJSONObject("pants").getInt("colour"));
    }

    @Test
    public void testDeserializeKitBasic() throws IOException {
        final TeamKit<PlainKitPart, PlainKitPart> expected = new TeamKit(new PlainKitPart(123), new PlainKitPart(123));
        final var json = new JSONObject();
        json.put("shirt", createPlainKitMap(expected.getShirt().getColour()));
        json.put("pants", createPlainKitMap(expected.getPants().getColour()));
        assertEquals(expected, OBJECT_MAPPER.readValue(json.toString(), TeamKit.class));
    }

    private Map<String, Object> createPlainKitMap(final int colour) {
        final Map<String, Object> map = new HashMap<>();
        map.put("type", "plain");
        map.put("colour", colour);
        return map;
    }

}
