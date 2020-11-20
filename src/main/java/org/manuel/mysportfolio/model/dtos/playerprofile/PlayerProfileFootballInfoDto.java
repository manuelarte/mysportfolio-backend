package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballPosition;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballSkills;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.util.Set;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = PlayerProfileFootballInfoDto.PlayerProfileFootballInfoDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileFootballInfoDto {

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final PlayerProfileSportSeasonSummaryDto summary;

  private final FootballPosition preferredPosition;
  @Size(max = 3)
  private final Set<FootballPosition> alternativePositions;
  private final FootballSkills skills;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileFootballInfoDtoBuilder {

  }

}
