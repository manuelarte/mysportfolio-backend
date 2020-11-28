package org.manuel.mysportfolio.model.dtos.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;

class MatchDtoTest {

  private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

  @Test
  public void testEqualsAndHashCode() {
    EqualsVerifier.forClass(MatchDto.class);
  }

  @Test
  public void testSerializeMatchWithTwoAnonymousTeams() throws JsonProcessingException {
    final var startDate = Instant.now().minus(1, ChronoUnit.DAYS);
    final MatchDto<AnonymousTeamDto, AnonymousTeamDto> matchDto =
        MatchDto.<AnonymousTeamDto, AnonymousTeamDto>builder()
            .id(new ObjectId())
            .homeTeam(createMockAnonymousTeamDto())
            .startDate(startDate)
            .awayTeam(createMockAnonymousTeamDto())
            .createdBy(UUID.randomUUID().toString())
            .build();
    JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
    assertEquals(json.getString("id"), matchDto.getId().toString());
    assertEquals(json.getString("startDate"), matchDto.getStartDate().toString());
    assertEquals(json.getJSONObject("homeTeam").getString("name"),
        matchDto.getHomeTeam().getName());
    assertFalse(json.getJSONObject("homeTeam").has("id"));
    assertEquals(json.getJSONObject("awayTeam").getString("name"),
        matchDto.getAwayTeam().getName());
    assertFalse(json.getJSONObject("awayTeam").has("id"));
    assertEquals(json.getString("createdBy"), matchDto.getCreatedBy());
  }

  @Test
  public void testSerializeMatchWithTwoRegisteredTeams() throws JsonProcessingException {
    final MatchDto<RegisteredTeamDto, RegisteredTeamDto> matchDto =
        MatchDto.<RegisteredTeamDto, RegisteredTeamDto>builder()
            .id(new ObjectId())
            .homeTeam(createMockRegisteredTeamDto())
            .awayTeam(createMockRegisteredTeamDto())
            .createdBy(UUID.randomUUID().toString())
            .build();
    JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
    assertEquals(json.getString("id"), matchDto.getId().toString());
    assertEquals(json.getJSONObject("homeTeam").getString("teamId"),
        matchDto.getHomeTeam().getTeamId().toString());
    assertFalse(json.getJSONObject("homeTeam").has("name"));
    assertEquals(json.getJSONObject("awayTeam").getString("teamId"),
        matchDto.getAwayTeam().getTeamId().toString());
    assertFalse(json.getJSONObject("awayTeam").has("name"));
    assertEquals(json.getString("createdBy"), matchDto.getCreatedBy());
  }

  @Test
  public void testSerializeMatchWithOneRegisteredTeamAndOneAnonymous()
      throws JsonProcessingException {
    final MatchDto<RegisteredTeamDto, AnonymousTeamDto> matchDto =
        MatchDto.<RegisteredTeamDto, AnonymousTeamDto>builder()
            .id(new ObjectId())
            .homeTeam(createMockRegisteredTeamDto())
            .awayTeam(createMockAnonymousTeamDto())
            .createdBy(UUID.randomUUID().toString())
            .build();
    JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
    assertEquals(json.getString("id"), matchDto.getId().toString());
    assertEquals(json.getJSONObject("homeTeam").getString("teamId"),
        matchDto.getHomeTeam().getTeamId().toString());
    assertFalse(json.getJSONObject("homeTeam").has("name"));
    assertEquals(json.getJSONObject("awayTeam").getString("name"),
        matchDto.getAwayTeam().getName());
    assertFalse(json.getJSONObject("awayTeam").has("teamId"));
    assertEquals(json.getString("createdBy"), matchDto.getCreatedBy());
  }

  private AnonymousTeamDto createMockAnonymousTeamDto() {
    return AnonymousTeamDto.builder()
        .name(RandomStringUtils.randomAlphabetic(5))
        .build();
  }

  private RegisteredTeamDto createMockRegisteredTeamDto() {
    return RegisteredTeamDto.builder()
        .teamId(new ObjectId())
        .build();
  }


}
