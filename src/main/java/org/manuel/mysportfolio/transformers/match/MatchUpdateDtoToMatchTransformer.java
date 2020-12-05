package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import io.github.manuelarte.mysportfolio.model.dtos.match.MatchDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamTypeDto;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.manuel.mysportfolio.transformers.match.events.MatchEventDtoToMatchEventTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchUpdateDtoToMatchTransformer implements
    BiFunction<Match<? extends TeamType, ? extends TeamType>,
        MatchDto<? extends TeamTypeDto, ? extends TeamTypeDto>, Match<TeamType, TeamType>> {

  private final TeamInMatchDtoToTeamTypeTransformer teamInMatchDtoToTeamTypeTransformer;
  private final MatchTypeDtoToMatchTypeTransformer matchTypeDtoToMatchTypeTransformer;
  private final MatchEventDtoToMatchEventTransformer matchEventDtoToMatchEventTransformer;
  private final PlaceDtoToPlaceTransformer placeDtoToPlaceTransformer;

  @Override
  public Match<TeamType, TeamType> apply(
      final Match<? extends TeamType, ? extends TeamType> originalMatch,
      final MatchDto<? extends TeamTypeDto, ? extends TeamTypeDto> matchUpdateDto) {
    final var updatedMatch = new Match<>();

    // Fields that should never change
    updatedMatch.setId(originalMatch.getId());
    updatedMatch.setCreatedBy(originalMatch.getCreatedBy().get());
    updatedMatch.setCreatedDate(originalMatch.getCreatedDate().get());
    updatedMatch.setLastModifiedBy(originalMatch.getLastModifiedBy().orElse(null));
    updatedMatch.setLastModifiedDate(originalMatch.getLastModifiedDate().orElse(null));

    // Fields that can be modified
    updatedMatch.setVersion(matchUpdateDto.getVersion());
    updatedMatch.setType(matchTypeDtoToMatchTypeTransformer.apply(matchUpdateDto.getType()));

    updatedMatch.setAddress(Optional.ofNullable(matchUpdateDto.getAddress()).map(placeDtoToPlaceTransformer).orElse(null));
    updatedMatch.setStartDate(matchUpdateDto.getStartDate());
    updatedMatch.setEndDate(matchUpdateDto.getEndDate());

    final Stream<MatchEvent> stream = Optional.ofNullable(matchUpdateDto.getEvents())
        .orElse(new ArrayList<>())
        .stream().map(matchEventDtoToMatchEventTransformer);
    updatedMatch.setEvents(stream.collect(Collectors.toList()));

    updatedMatch.setPlayedFor(matchUpdateDto.getPlayedFor());

    updatedMatch
        .setHomeTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchUpdateDto.getHomeTeam()));
    updatedMatch
        .setAwayTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchUpdateDto.getAwayTeam()));

    updatedMatch.setDescription(matchUpdateDto.getDescription());
    updatedMatch.setChips(matchUpdateDto.getChips());

    return updatedMatch;
  }
}
