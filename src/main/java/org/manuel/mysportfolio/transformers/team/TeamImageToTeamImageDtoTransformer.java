package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.TeamImage;
import io.github.manuelarte.mysportfolio.model.documents.team.UrlImage;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.TeamImageDto;
import org.manuel.mysportfolio.model.dtos.team.UrlImageDto;
import org.springframework.stereotype.Component;

@Component
public class TeamImageToTeamImageDtoTransformer implements Function<TeamImage, TeamImageDto> {

  @Override
  public TeamImageDto apply(final TeamImage teamImage) {
    if (teamImage instanceof UrlImage) {
      final var urlImage = (UrlImage) teamImage;
      return new UrlImageDto(urlImage.getUrl());
    }
    return null;
  }
}
