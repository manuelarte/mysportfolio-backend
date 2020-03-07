package org.manuel.mysportfolio.transformers;

import java.util.Optional;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.stereotype.Component;

@Component
public class CompetitionDtoToCompetitionTransformer implements
    Function<CompetitionDto, Competition> {

  @Override
  public Competition apply(CompetitionDto competitionDto) {
    final var competition = new Competition();
    Optional.ofNullable(competitionDto.getId())
        .ifPresent(id -> competition.setId(new ObjectId(id)));
    competition.setVersion(competitionDto.getVersion());
    competition.setName(competitionDto.getName());
    competition.setDescription(competitionDto.getDescription());
    competition.setSport(competitionDto.getSport());
    competition.setDefaultMatchDay(competitionDto.getDefaultMatchDay());

    return competition;
  }
}
