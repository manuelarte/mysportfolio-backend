package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import javax.validation.constraints.Null;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = PlayerProfileSportSeasonSummaryDto.PlayerProfileSportSeasonSummaryDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
public class PlayerProfileSportSeasonSummaryDto {

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  private final Integer numberOfMatchesPlayed;

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  private final Integer numberOfGoals;

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  private final BigDecimal averageGoalRate;

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  private final Integer numberOfAssists;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileSportSeasonSummaryDtoBuilder {

  }

}
