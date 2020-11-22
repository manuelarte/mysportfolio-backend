package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.util.Optional;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.springframework.stereotype.Component;

@Component
public class CompetitionDtoToCompetitionTransformer implements
    Function<CompetitionDto, Competition> {

  @Override
  public Competition apply(final CompetitionDto competitionDto) {
    final var competition = new Competition();
    Optional.ofNullable(competitionDto.getId())
        .ifPresent(id -> competition.setId(new ObjectId(id)));
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
