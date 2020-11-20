package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.KitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.TeamKit;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.KitPartDto;
import org.manuel.mysportfolio.model.dtos.team.TeamKitDto;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class TeamKitToTeamKitDtoTransformer implements
    Function<TeamKit<KitPart, KitPart>, TeamKitDto<KitPartDto, KitPartDto>> {

  private final KitPartToKitPartDtoTransformer kitPartToKitPartDtoTransformer;

  @Override
  public TeamKitDto<KitPartDto, KitPartDto> apply(final TeamKit<KitPart, KitPart> teamKit) {
    if (teamKit != null) {
      return new TeamKitDto<>(kitPartToKitPartDtoTransformer.apply(teamKit.getShirt()),
          kitPartToKitPartDtoTransformer.apply(teamKit.getPants()));
    }
    return null;
  }
}
