package org.manuel.mysportfolio.transformers.competition;

import java.util.function.BiFunction;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class CompetitionDtoToExistingCompetitionTransformer implements
    BiFunction<String, CompetitionDto, Competition> {

  private final CompetitionQueryService competitionQueryService;

  @Override
  public Competition apply(final String competitionId, final CompetitionDto competitionDto) {
    final var originalCompetition = competitionQueryService.findOne(new ObjectId(competitionId))
        .orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Competition with id %s not found", competitionId)));

    final var competition = new Competition();
    competition.setId(originalCompetition.getId());
    competition.setVersion(competitionDto.getVersion());
    competition.setName(competitionDto.getName());
    competition.setDescription(competitionDto.getDescription());
    competition.setSport(competitionDto.getSport());
    competition.setFrom(competitionDto.getFrom());
    competition.setTo(competitionDto.getTo());
    competition.setDefaultMatchDay(competitionDto.getDefaultMatchDay());
    competition.setCreatedBy(originalCompetition.getCreatedBy().orElse(null));
    competition.setCreatedDate(originalCompetition.getCreatedDate().orElse(null));
    competition.setLastModifiedBy(originalCompetition.getLastModifiedBy().orElse(null));
    competition.setLastModifiedDate(originalCompetition.getLastModifiedDate().orElse(null));

    return competition;
  }
}
