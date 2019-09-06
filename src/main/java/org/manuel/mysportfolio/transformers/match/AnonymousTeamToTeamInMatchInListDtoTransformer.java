package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamToTeamInMatchInListDtoTransformer implements Function<AnonymousTeam, TeamDto> {

    @Override
    public TeamDto apply(final AnonymousTeam anonymousTeam) {
        return anonymousTeam == null ? null : TeamDto.builder()
                .name(anonymousTeam.getName())
                .imageLink(anonymousTeam.getImageLink())
                .build();
    }

}
