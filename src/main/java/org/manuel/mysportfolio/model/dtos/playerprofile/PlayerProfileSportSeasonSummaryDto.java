package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.math.BigDecimal;
import javax.validation.constraints.Null;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = PlayerProfileSportSeasonSummaryDto.PlayerProfileSportSeasonSummaryDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
public class PlayerProfileSportSeasonSummaryDto {

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final Integer numberOfMatchesPlayed;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final Integer numberOfGoals;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final BigDecimal goalRatio;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final BigDecimal averageGoalRate;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final Integer numberOfAssists;

  // TODO Max performance, average performance, lower performance

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileSportSeasonSummaryDtoBuilder {

  }

}
