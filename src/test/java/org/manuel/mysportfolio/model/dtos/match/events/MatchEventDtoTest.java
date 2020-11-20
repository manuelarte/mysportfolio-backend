package org.manuel.mysportfolio.model.dtos.match.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import java.io.IOException;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

class MatchEventDtoTest {

  private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

  @Test
  public void testDeserializeGoal() throws IOException {
    final var json = new JSONObject();
    json.put("id", new ObjectId().toString());
    json.put("type", "goal");
    json.put("team", "HOME_TEAM");
    json.put("minute", 90);
    final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEventDto.class);
    assertTrue(matchEvent instanceof GoalMatchEventDto);
    final var goalEvent = (GoalMatchEventDto) matchEvent;
    assertEquals(goalEvent.getId(), json.get("id"));
    assertEquals(goalEvent.getTeam().toString(), json.get("team"));
    assertEquals(goalEvent.getMinute(), json.get("minute"));
  }

  @Test
  public void testDeserializeSubstitutionWithIn() throws IOException {
    final var json = new JSONObject();
    json.put("id", new ObjectId().toString());
    json.put("type", "substitution");
    json.put("team", "HOME_TEAM");
    json.put("minute", 70);
    json.put("in", "123456789");
    final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEventDto.class);
    final var substitutionEvent = (SubstitutionMatchEventDto) matchEvent;
    assertEquals(substitutionEvent.getId(), json.get("id"));
    assertEquals(substitutionEvent.getTeam().toString(), json.get("team"));
    assertEquals(substitutionEvent.getMinute(), json.get("minute"));
    assertEquals(substitutionEvent.getIn(), json.get("in"));
  }

  @Test
  public void testDeserializeSubstitutionWithOut() throws IOException {
    final var json = new JSONObject();
    json.put("id", new ObjectId().toString());
    json.put("type", "substitution");
    json.put("team", "HOME_TEAM");
    json.put("minute", 60);
    json.put("out", "12345");
    final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEventDto.class);
    assertTrue(matchEvent instanceof SubstitutionMatchEventDto);
    final var substitutionMatchEvent = (SubstitutionMatchEventDto) matchEvent;
    assertEquals(substitutionMatchEvent.getId(), json.get("id"));
    assertEquals(substitutionMatchEvent.getTeam().toString(), json.get("team"));
    assertEquals(substitutionMatchEvent.getMinute(), json.get("minute"));
    assertTrue(json.isNull("in"));
    assertEquals(substitutionMatchEvent.getOut(), json.get("out"));
  }

  @Test
  public void testSerializeGoal() throws JsonProcessingException {
    final var event = new GoalMatchEventDto(new ObjectId().toString(),
        TeamOption.HOME_TEAM, "12345", 30, null, null, null, "A very nice volley", null);
    final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(event));
    assertEquals(json.get("id"), event.getId());
    assertEquals(json.get("type"), "goal");
    assertEquals(json.get("team"), event.getTeam().toString());
    assertEquals(json.get("playerId"), event.getPlayerId());
    assertEquals(json.get("minute"), event.getMinute());
  }

  @Test
  public void testSerializeSubstitution() throws JsonProcessingException {
    final var event = new SubstitutionMatchEventDto(new ObjectId().toString(),
        TeamOption.HOME_TEAM, 75, "12345", null, null);
    final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(event));
    assertEquals(json.get("id"), event.getId());
    assertEquals(json.get("type"), "substitution");
    assertEquals(json.get("team"), event.getTeam().toString());
    assertEquals(json.get("minute"), event.getMinute());
    assertEquals(json.get("in"), event.getIn());
    assertTrue(json.isNull("out"));
  }

  @Test
  public void testDeserializeDefault() throws IOException {
    final var json = new JSONObject();
    json.put("id", new ObjectId().toString());
    json.put("type", "notRegistered");
    json.put("description", "referee stop the game");
    json.put("minute", 50);
    final var matchEvent = OBJECT_MAPPER.readValue(json.toString(), MatchEventDto.class);
    assertTrue(matchEvent instanceof DefaultMatchEventDto);
    final var defaultMatchEvent = (DefaultMatchEventDto) matchEvent;
    assertEquals(defaultMatchEvent.getId(), json.get("id"));
    assertEquals(defaultMatchEvent.get("description"), json.get("description"));
    assertEquals(defaultMatchEvent.get("minute"), json.get("minute"));
  }


}
