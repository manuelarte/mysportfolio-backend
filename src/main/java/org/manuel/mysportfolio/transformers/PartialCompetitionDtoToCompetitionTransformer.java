package org.manuel.mysportfolio.transformers;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiFunction;

@Component
@lombok.AllArgsConstructor
public class PartialCompetitionDtoToCompetitionTransformer implements BiFunction<String, CompetitionDto, Competition> {

    private final CompetitionQueryService competitionQueryService;

    @Override
    public Competition apply(final String competitionId, final CompetitionDto competitionDto) {
        final var original = competitionQueryService.findOne(new ObjectId(competitionId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Competition with id %s not found and can't be patch", competitionId)));
        final var competition = original;
        Optional.ofNullable(competitionDto.getName()).ifPresent(competition::setName);
        Optional.ofNullable(competitionDto.getDescription()).ifPresent(competition::setDescription);
        Optional.ofNullable(competitionDto.getSport()).ifPresent(competition::setSport);
        Optional.ofNullable(competitionDto.getDefaultMatchDay()).ifPresent(competition::setDefaultMatchDay);
        return competition;
    }
}
