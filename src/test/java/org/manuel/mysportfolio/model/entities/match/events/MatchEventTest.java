package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;
import org.manuel.mysportfolio.model.entities.TeamOption;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchEventTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testSerializeGoal() throws JsonProcessingException {
        final var event = new GoalMatchEvent(new ObjectId(),
                TeamOption.HOME_TEAM, "12345", 30, null, null, null, "A very nice volley", null);
        final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(event));
        assertEquals(json.get("id"), event.getId().toString());
        assertEquals(json.get("type"), "goal");
        assertEquals(json.get("team"), event.getTeam().toString());
        assertEquals(json.get("playerId"), event.getPlayerId());
        assertEquals(json.get("minute"), event.getMinute());
    }

    @Test
    public void testDeserializeGoal() throws IOException {
        final var json = new JSONObject();
        json.put("id", new ObjectId().toString());
        json.put("type", "goal");
        json.put("team", "HOME_TEAM");
        json.put("minute", 90);
        final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEvent.class);
        assertTrue(matchEvent instanceof GoalMatchEvent);
        final var goalMatchEvent = (GoalMatchEvent) matchEvent;
        assertEquals(goalMatchEvent.getId().toString(), json.get("id"));
        assertEquals(goalMatchEvent.getTeam().toString(), json.get("team"));
        assertEquals(goalMatchEvent.getMinute(), json.get("minute"));
    }

    @Test
    public void testSerializeSubstitution() throws JsonProcessingException {
        final var event = new SubstitutionMatchEvent(new ObjectId(),
                TeamOption.HOME_TEAM, 75, "12345", null, null);
        final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(event));
        assertEquals(json.get("id"), event.getId().toString());
        assertEquals(json.get("type"), "substitution");
        assertEquals(json.get("team"), event.getTeam().toString());
        assertEquals(json.get("minute"), event.getMinute());
        assertEquals(json.get("in"), event.getIn());
        assertEquals(json.get("out"), event.getOut());
    }

    @Test
    public void testDeserializeSubstitution() throws IOException {
        final var json = new JSONObject();
        json.put("id", new ObjectId().toString());
        json.put("type", "substitution");
        json.put("team", "HOME_TEAM");
        json.put("minute", 60);
        json.put("out", "12345");
        final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEvent.class);
        assertTrue(matchEvent instanceof SubstitutionMatchEvent);
        final var substitutionMatchEvent = (SubstitutionMatchEvent) matchEvent;
        assertEquals(substitutionMatchEvent.getId().toString(), json.get("id"));
        assertEquals(substitutionMatchEvent.getTeam().toString(), json.get("team"));
        assertEquals(substitutionMatchEvent.getMinute(), json.get("minute"));
        assertTrue(json.isNull("in"));
        assertEquals(substitutionMatchEvent.getOut(), json.get("out"));
    }

    @Test
    public void testDeserializeDefault() throws IOException {
        final var json = new JSONObject();
        json.put("id", new ObjectId().toString());
        json.put("type", "notRegistered");
        json.put("description", "referee stop the game");
        json.put("minute", 50);
        final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEvent.class);
        assertTrue(matchEvent instanceof DefaultMatchEvent);
        final var defaultMatchEvent = (DefaultMatchEvent) matchEvent;
        assertEquals(defaultMatchEvent.getId().toString(), json.get("id"));
        assertEquals(defaultMatchEvent.get("description"), json.get("description"));
        assertEquals(defaultMatchEvent.get("minute"), json.get("minute"));
    }


}
