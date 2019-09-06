package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamDtoToAnonymousTeamTransformer implements Function<AnonymousTeamDto, AnonymousTeam> {

    @Override
    public AnonymousTeam apply(final AnonymousTeamDto anonymousTeamDto) {
        return anonymousTeamDto == null ? null : create(anonymousTeamDto);
    }

    private AnonymousTeam create(final AnonymousTeamDto anonymousTeamDto) {
        final var anonymousTeam = new AnonymousTeam();
        anonymousTeam.setName(anonymousTeamDto.getName());
        anonymousTeam.setImageLink(anonymousTeamDto.getImageLink());
        return anonymousTeam;
    }

}
