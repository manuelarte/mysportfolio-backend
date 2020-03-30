package org.manuel.mysportfolio.transformers.competition;

import java.util.Optional;
import java.util.function.BiFunction;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@lombok.AllArgsConstructor
public class PartialCompetitionDtoToCompetitionTransformer implements
    BiFunction<String, CompetitionDto, Competition> {

  private final CompetitionQueryService competitionQueryService;

  @Override
  public Competition apply(@NotNull final String competitionId,
      @NotNull final CompetitionDto updatedFieldsCompetitionDto) {
    Assert.notNull(competitionId, "The competition id in a partial update can't be null");
    Assert.notNull(updatedFieldsCompetitionDto,
        "The updated pojo for competition in a partial update can't be null");
    final var originalCompetition = competitionQueryService.findOne(new ObjectId(competitionId))
        .orElseThrow(() -> new EntityNotFoundException(String
            .format("Competition with id %s not found and can't be patch",
                updatedFieldsCompetitionDto.getId())));
    final var mixed = originalCompetition;
    Optional.ofNullable(updatedFieldsCompetitionDto.getName()).ifPresent(mixed::setName);
    Optional.ofNullable(updatedFieldsCompetitionDto.getDescription())
        .ifPresent(mixed::setDescription);
    Optional.ofNullable(updatedFieldsCompetitionDto.getSport()).ifPresent(mixed::setSport);
    Optional.ofNullable(updatedFieldsCompetitionDto.getDefaultMatchDay())
        .ifPresent(mixed::setDefaultMatchDay);
    Optional.ofNullable(updatedFieldsCompetitionDto.getFrom()).ifPresent(mixed::setFrom);
    Optional.ofNullable(updatedFieldsCompetitionDto.getTo()).ifPresent(mixed::setTo);
    return mixed;
  }
}
