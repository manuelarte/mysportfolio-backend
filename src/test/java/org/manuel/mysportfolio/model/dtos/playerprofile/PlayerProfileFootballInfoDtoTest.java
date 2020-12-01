package org.manuel.mysportfolio.model.dtos.playerprofile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

public class PlayerProfileFootballInfoDtoTest {

  private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

  @Test
  public void testEqualsAndHashCode() {
    EqualsVerifier.forClass(PlayerProfileFootballInfoDto.class);
  }

  @Test
  public void testSerializeSummaryEntryOneEntry() throws JsonProcessingException {
    final var januaryEntry = PlayerProfileTimeIntervalSummaryDto.builder()
        .numberOfMatchesPlayed(2)
        .numberOfGoals(1)
        .build();
    final PlayerProfileFootballInfoDto playerProfileFootballInfoDto = PlayerProfileFootballInfoDto.builder()
        .summary(Collections.singletonMap(Month.JANUARY, januaryEntry)).build();
    JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(playerProfileFootballInfoDto));

    assertEquals(json.getJSONObject("summary").getJSONObject("JANUARY").getInt("numberOfMatchesPlayed"),
        januaryEntry.getNumberOfMatchesPlayed());
    assertEquals(json.getJSONObject("summary").getJSONObject("JANUARY").getInt("numberOfGoals"), januaryEntry.getNumberOfGoals());
    assertTrue(json.getJSONObject("summary").getJSONObject("JANUARY").isNull("numberOfAssists"));
  }

  @Test
  public void testDeserializeWithOneEntry() throws JsonProcessingException {
    final var json = new JSONObject();
    final var january = new HashMap<String, Integer>();
    january.put("numberOfMatchesPlayed", 2);
    january.put("numberOfGoals", 1);
    final var summary = new HashMap<String, Object>();
    summary.put("JANUARY", january);
    json.put("summary", summary);

    final PlayerProfileFootballInfoDto expected = PlayerProfileFootballInfoDto.builder()
        .summary(Collections.singletonMap(Month.JANUARY, PlayerProfileTimeIntervalSummaryDto.builder()
            .numberOfMatchesPlayed(2)
            .numberOfGoals(1)
            .build())).build();
    final PlayerProfileFootballInfoDto actual = OBJECT_MAPPER.readValue(json.toString(), PlayerProfileFootballInfoDto.class);

    assertEquals(expected.getSummary().get(Month.JANUARY), actual.getSummary().get(Month.JANUARY));
  }

}
