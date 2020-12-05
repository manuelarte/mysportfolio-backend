package org.manuel.mysportfolio.model.entities.team;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamKitDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.kits.PlainKitPartDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

class TeamKitDtoTest {

  private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

  @Test
  public void testSerializeKitBasic() throws JsonProcessingException {
    final TeamKitDto<PlainKitPartDto, PlainKitPartDto> teamKit = new TeamKitDto<>(new PlainKitPartDto(123),
        new PlainKitPartDto(123));
    final var json = new JSONObject(OBJECT_MAPPER.writeValueAsString(teamKit));
    assertEquals("plain", json.getJSONObject("shirt").getString("type"));
    assertEquals("plain", json.getJSONObject("pants").getString("type"));
    assertEquals(teamKit.getShirt().getColour(), json.getJSONObject("shirt").getInt("colour"));
    assertEquals(teamKit.getPants().getColour(), json.getJSONObject("pants").getInt("colour"));
  }

  @Test
  public void testDeserializeKitBasic() throws IOException {
    final TeamKitDto<PlainKitPartDto, PlainKitPartDto> expected = new TeamKitDto<>(new PlainKitPartDto(123),
        new PlainKitPartDto(123));
    final var json = new JSONObject();
    json.put("shirt", createPlainKitMap(expected.getShirt().getColour()));
    json.put("pants", createPlainKitMap(expected.getPants().getColour()));
    assertEquals(expected, OBJECT_MAPPER.readValue(json.toString(), TeamKitDto.class));
  }

  private Map<String, Object> createPlainKitMap(final int colour) {
    final Map<String, Object> map = new HashMap<>();
    map.put("type", "plain");
    map.put("colour", colour);
    return map;
  }

}
