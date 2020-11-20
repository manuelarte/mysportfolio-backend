package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.events.MatchEventDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.transformers.PlaceToPlaceDtoTransformer;
import org.manuel.mysportfolio.transformers.match.events.MatchEventToMatchEventDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchDtoTransformer implements
    Function<Match<? extends TeamType, ? extends TeamType>, MatchDto<TeamTypeDto, TeamTypeDto>> {

  private final TeamTypeToTeamInMatchDtoTransformer teamTypeToTeamInMatchDtoTransformer;
  private final MatchTypeToMatchTypeDtoTransformer matchTypeToMatchTypeDtoTransformer;
  private final MatchEventToMatchEventDtoTransformer matchEventToMatchEventDtoTransformer;
  private final PlaceToPlaceDtoTransformer placeToPlaceDtoTransformer;

  @Override
  public MatchDto<TeamTypeDto, TeamTypeDto> apply(
      final Match<? extends TeamType, ? extends TeamType> match) {
    final Stream<MatchEventDto> eventsStream = match.getEvents().stream()
        .map(matchEventToMatchEventDtoTransformer);
    return match == null ? null : MatchDto.builder()
        .id(match.getId().toString())
        .version(match.getVersion())
        .type(matchTypeToMatchTypeDtoTransformer.apply(match.getType()))
        .playedFor(match.getPlayedFor())
        .homeTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getHomeTeam()))
        .awayTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getAwayTeam()))
        .address(placeToPlaceDtoTransformer.apply(match.getAddress()))
        .startDate(match.getStartDate())
        .endDate(match.getEndDate())
        .events(eventsStream.collect(Collectors.toList()))
        .description(match.getDescription())
        .chips(match.getChips())
        .createdBy(match.getCreatedBy().orElse(null))
        .build();
  }

}
