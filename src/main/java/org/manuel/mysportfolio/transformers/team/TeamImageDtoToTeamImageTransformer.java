package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.TeamImage;
import io.github.manuelarte.mysportfolio.model.documents.team.UrlImage;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamImageDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.UrlImageDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TeamImageDtoToTeamImageTransformer implements Function<TeamImageDto, TeamImage> {

  @Override
  public TeamImage apply(final TeamImageDto teamImageDto) {
    if (teamImageDto instanceof UrlImageDto) {
      final var urlImageDto = (UrlImageDto) teamImageDto;
      return new UrlImage(urlImageDto.getUrl());
    }
    return null;
  }
}
