package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import io.github.manuelarte.mysportfolio.model.dtos.CompetitionDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class CompetitionToCompetitionDtoTransformer implements
    Function<Competition, CompetitionDto> {

  @Override
  public CompetitionDto apply(final Competition competition) {
    return CompetitionDto.builder()
        .id(competition.getId())
        .version(competition.getVersion())
        .name(competition.getName())
        .description(competition.getDescription())
        .sport(competition.getSport())
        .defaultMatchDay(competition.getDefaultMatchDay())
        .from(competition.getFrom())
        .to(competition.getTo())
        .createdBy(competition.getCreatedBy().orElse(null))
        .createdDate(competition.getCreatedDate().orElse(null))
        .build();
  }
}
