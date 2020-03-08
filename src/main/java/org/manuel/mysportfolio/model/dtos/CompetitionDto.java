package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.YearMonth;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

@JsonDeserialize(builder = CompetitionDto.CompetitionDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class CompetitionDto {

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  private final String id;

  @Null(groups = NewEntity.class)
  @NotNull(groups = {UpdateEntity.class, PartialUpdateEntity.class})
  private final Long version;

  @NotNull(groups = NewEntity.class)
  @Size(max = 30)
  private final String name;

  @NotNull(groups = NewEntity.class)
  private final Sport sport;

  private final DayOfWeek defaultMatchDay;

  private YearMonth from;

  private YearMonth to;

  @Size(max = 200)
  private final String description;

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  private final String createdBy;

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  private final Instant createdDate;

  @AssertTrue
  private boolean fromBeforeTo() {
    boolean condition;
    if (from != null && to != null) {
      condition = from.compareTo(to) <= 0;
    } else {
      condition = true;
    }
    return condition;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CompetitionDtoBuilder {

  }
}
