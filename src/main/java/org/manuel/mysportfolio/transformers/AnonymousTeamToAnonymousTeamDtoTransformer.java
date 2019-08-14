package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamToAnonymousTeamDtoTransformer implements Function<AnonymousTeam, AnonymousTeamDto> {

    @Override
    public AnonymousTeamDto apply(final AnonymousTeam anonymousTeam) {
        return anonymousTeam == null ? null : AnonymousTeamDto.builder()
                .name(anonymousTeam.getName())
                .imageLink(anonymousTeam.getImageLink())
                .build();
    }

}
