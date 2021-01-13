package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import io.github.manuelarte.mysportfolio.model.dtos.CompetitionDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class CompetitionDtoToCompetitionTransformer implements
    Function<CompetitionDto, Competition> {

  @Override
  public Competition apply(final CompetitionDto competitionDto) {
    final var competition = new Competition();
    competition.setId(competitionDto.getId());
    competition.setVersion(competitionDto.getVersion());
    competition.setName(competitionDto.getName());
    competition.setDescription(competitionDto.getDescription());
    competition.setSport(competitionDto.getSport());
    competition.setFrom(competitionDto.getFrom());
    competition.setTo(competitionDto.getTo());
    competition.setDefaultMatchDay(competitionDto.getDefaultMatchDay());

    return competition;
  }
}
