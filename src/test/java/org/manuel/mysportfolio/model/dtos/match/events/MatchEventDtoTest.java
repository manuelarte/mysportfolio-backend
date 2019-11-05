package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;
import org.manuel.mysportfolio.model.dtos.match.MatchEventDto;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchEventDtoTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testDeserializeGoal() throws IOException {
        final var json = new JSONObject();
        json.put("id", new ObjectId().toString());
        json.put("type", "goal");
        json.put("team", "HOME_TEAM");
        json.put("minute", 90);
        final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEventDto.class);
        assertEquals(matchEvent.get("id"), json.get("id"));
        assertEquals(matchEvent.get("team"), json.get("team"));
        assertEquals(matchEvent.get("minute"), json.get("minute"));
    }
}
