package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.TeamImage;
import io.github.manuelarte.mysportfolio.model.documents.team.UrlImage;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.TeamImageDto;
import org.manuel.mysportfolio.model.dtos.team.UrlImageDto;
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
