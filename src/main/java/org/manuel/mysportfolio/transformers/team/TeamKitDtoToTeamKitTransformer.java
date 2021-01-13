package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.KitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.TeamKit;
import io.github.manuelarte.mysportfolio.model.dtos.team.KitPartDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamKitDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class TeamKitDtoToTeamKitTransformer implements
    Function<TeamKitDto<KitPartDto, KitPartDto>, TeamKit<KitPart, KitPart>> {

  private final KitPartDtoToKitPartTransformer kitPartDtoToKitPartTransformer;

  @Override
  public TeamKit<KitPart, KitPart> apply(final TeamKitDto<KitPartDto, KitPartDto> teamKit) {
    if (teamKit != null) {
      return new TeamKit<>(kitPartDtoToKitPartTransformer.apply(teamKit.getShirt()),
          kitPartDtoToKitPartTransformer.apply(teamKit.getPants()));
    }
    return null;
  }
}
