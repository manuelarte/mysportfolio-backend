package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MatchDtoTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(MatchDto.class);
    }

    @Test
    public void testSerializeMatchWithTwoAnonymousTeams() throws JsonProcessingException {
        final MatchDto<AnonymousTeamDto, AnonymousTeamDto> matchDto =
                MatchDto.<AnonymousTeamDto, AnonymousTeamDto>builder()
                .id(UUID.randomUUID().toString())
                .homeTeam(createMockAnonymousTeamDto())
                .awayTeam(createMockAnonymousTeamDto())
                .creator(UUID.randomUUID().toString())
                .build();
        JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
        assertEquals(json.getString("id"), matchDto.getId());
        assertEquals(json.getJSONObject("homeTeam").getString("name"), matchDto.getHomeTeam().getName());
        assertFalse(json.getJSONObject("homeTeam").has("id"));
        assertEquals(json.getJSONObject("awayTeam").getString("name"), matchDto.getAwayTeam().getName());
        assertFalse(json.getJSONObject("awayTeam").has("id"));
        assertEquals(json.getString("creator"), matchDto.getCreator());
    }

    @Test
    public void testSerializeMatchWithTwoRegisteredTeams() throws JsonProcessingException {
        final MatchDto<RegisteredTeamDto, RegisteredTeamDto> matchDto =
                MatchDto.<AnonymousTeamDto, AnonymousTeamDto>builder()
                        .id(UUID.randomUUID().toString())
                        .homeTeam(createMockRegisteredTeamDto())
                        .awayTeam(createMockRegisteredTeamDto())
                        .creator(UUID.randomUUID().toString())
                        .build();
        JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
        assertEquals(json.getString("id"), matchDto.getId());
        assertEquals(json.getJSONObject("homeTeam").getString("teamId"), matchDto.getHomeTeam().getTeamId());
        assertFalse(json.getJSONObject("homeTeam").has("name"));
        assertEquals(json.getJSONObject("awayTeam").getString("teamId"), matchDto.getAwayTeam().getTeamId());
        assertFalse(json.getJSONObject("awayTeam").has("name"));
        assertEquals(json.getString("creator"), matchDto.getCreator());
    }

    @Test
    public void testSerializeMatchWithOneRegisteredTeamAndOneAnonymous() throws JsonProcessingException {
        final MatchDto<RegisteredTeamDto, AnonymousTeamDto> matchDto =
                MatchDto.<RegisteredTeamDto, AnonymousTeamDto>builder()
                        .id(UUID.randomUUID().toString())
                        .homeTeam(createMockRegisteredTeamDto())
                        .awayTeam(createMockAnonymousTeamDto())
                        .creator(UUID.randomUUID().toString())
                        .build();
        JSONObject json = new JSONObject(OBJECT_MAPPER.writeValueAsString(matchDto));
        assertEquals(json.getString("id"), matchDto.getId());
        assertEquals(json.getJSONObject("homeTeam").getString("teamId"), matchDto.getHomeTeam().getTeamId());
        assertFalse(json.getJSONObject("homeTeam").has("name"));
        assertEquals(json.getJSONObject("awayTeam").getString("name"), matchDto.getAwayTeam().getName());
        assertFalse(json.getJSONObject("awayTeam").has("teamId"));
        assertEquals(json.getString("creator"), matchDto.getCreator());
    }

    private AnonymousTeamDto createMockAnonymousTeamDto() {
        return AnonymousTeamDto.builder()
                .name(RandomStringUtils.randomAlphabetic(5))
                .build();
    }

    private RegisteredTeamDto createMockRegisteredTeamDto() {
        return RegisteredTeamDto.builder()
                .teamId(UUID.randomUUID().toString())
                .build();
    }


}
