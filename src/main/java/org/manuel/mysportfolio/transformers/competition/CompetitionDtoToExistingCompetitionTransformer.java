package org.manuel.mysportfolio.transformers.competition;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import io.github.manuelarte.mysportfolio.model.dtos.CompetitionDto;
import java.util.Objects;
import java.util.function.BiFunction;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class CompetitionDtoToExistingCompetitionTransformer implements
    BiFunction<ObjectId, CompetitionDto, Competition> {

  private final CompetitionQueryService competitionQueryService;

  @Override
  public Competition apply(final ObjectId competitionId, final CompetitionDto competitionDto) {
    final var originalCompetition = competitionQueryService.findOne(competitionId)
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
    competition.setCreatedBy(Objects.requireNonNull(originalCompetition.getCreatedBy().orElse(null)));
    competition.setCreatedDate(Objects.requireNonNull(originalCompetition.getCreatedDate().orElse(null)));
    competition.setLastModifiedBy(Objects.requireNonNull(originalCompetition.getLastModifiedBy().orElse(null)));
    competition.setLastModifiedDate(Objects.requireNonNull(originalCompetition.getLastModifiedDate().orElse(null)));

    return competition;
  }
}
