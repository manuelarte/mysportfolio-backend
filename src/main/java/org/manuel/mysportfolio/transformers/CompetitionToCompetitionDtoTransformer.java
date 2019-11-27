package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompetitionToCompetitionDtoTransformer implements Function<Competition, CompetitionDto> {

    @Override
    public CompetitionDto apply(Competition competition) {
        return CompetitionDto.builder()
                .id(competition.getId().toString())
                .version(competition.getVersion())
                .name(competition.getName())
                .description(competition.getDescription())
                .sport(competition.getSport())
                .defaultMatchDay(competition.getDefaultMatchDay())
                .createdBy(competition.getCreatedBy().orElse(null))
                .createdDate(competition.getCreatedDate().orElse(null))
                .build();
    }
}
