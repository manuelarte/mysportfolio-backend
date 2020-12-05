package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import io.github.manuelarte.mysportfolio.model.dtos.match.MatchDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamTypeDto;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.manuel.mysportfolio.transformers.match.events.MatchEventDtoToMatchEventTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchDtoToMatchTransformer implements
    Function<MatchDto<TeamTypeDto, TeamTypeDto>, Match<TeamType, TeamType>> {

  private final TeamInMatchDtoToTeamTypeTransformer teamInMatchDtoToTeamTypeTransformer;
  private final MatchTypeDtoToMatchTypeTransformer matchTypeDtoToMatchTypeTransformer;
  private final MatchEventDtoToMatchEventTransformer matchEventDtoToMatchEventTransformer;
  private final PlaceDtoToPlaceTransformer placeDtoToPlaceTransformer;

  @Override
  public Match<TeamType, TeamType> apply(final MatchDto<TeamTypeDto, TeamTypeDto> matchDto) {
    final var match = new Match<>();
    match.setId(matchDto.getId());
    match.setType(
        matchTypeDtoToMatchTypeTransformer.apply(matchDto.getType()));
    match.setVersion(matchDto.getVersion());

    match.setAddress(Optional.ofNullable(matchDto.getAddress()).map(placeDtoToPlaceTransformer).orElse(null));
    match.setStartDate(matchDto.getStartDate());
    match.setEndDate(matchDto.getEndDate());

    final Stream<MatchEvent> stream = Optional.ofNullable(matchDto.getEvents())
        .orElse(new ArrayList<>())
        .stream().map(matchEventDtoToMatchEventTransformer);
    match.setEvents(stream.collect(Collectors.toList()));

    match.setPlayedFor(matchDto.getPlayedFor());

    match.setHomeTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getHomeTeam()));
    match.setAwayTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getAwayTeam()));

    match.setDescription(matchDto.getDescription());
    match.setChips(matchDto.getChips());

    return match;
  }

}
