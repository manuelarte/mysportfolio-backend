package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.YearMonth;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.Sport;

@JsonDeserialize(builder = CompetitionDto.CompetitionDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Value
@lombok.EqualsAndHashCode(callSuper = true)
public class CompetitionDto extends BaseDto {

  @NotNull(groups = {New.class, Update.class})
  @Size(max = 30)
  private final String name;

  @NotNull(groups = {New.class, Update.class})
  private final Sport sport;

  private final DayOfWeek defaultMatchDay;

  private final YearMonth from;

  private final YearMonth to;

  @Size(max = 200)
  private final String description;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final Instant createdDate;

  @lombok.Builder(toBuilder = true)
  public CompetitionDto(final String id, final Long version, final String name, final Sport sport,
      final DayOfWeek defaultMatchDay, final YearMonth from, final YearMonth to,
      final String description,
      final Instant createdDate, final String createdBy) {
    super(id, version, createdBy);
    this.name = name;
    this.sport = sport;
    this.defaultMatchDay = defaultMatchDay;
    this.from = from;
    this.to = to;
    this.description = description;
    this.createdDate = createdDate;
  }

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
