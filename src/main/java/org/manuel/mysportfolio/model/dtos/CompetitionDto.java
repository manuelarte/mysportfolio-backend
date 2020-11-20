package org.manuel.mysportfolio.model.dtos;

import static io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.Constants;
import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.FromDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.ToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.YearMonth;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@JsonDeserialize(builder = CompetitionDto.CompetitionDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FromAndToDate(FROM_LOWER_THAN_OR_EQUAL_TO_TO)
@lombok.Value
@lombok.EqualsAndHashCode(callSuper = true)
public class CompetitionDto extends BaseDto {

  @NotNull(groups = {New.class, Update.class})
  @Size(max = Constants.COMPETITION_NAME_MAX_CHARACTERS)
  private final String name;

  @NotNull(groups = {New.class, Update.class})
  private final Sport sport;

  private final DayOfWeek defaultMatchDay;

  @FromDate
  private final YearMonth from;

  @ToDate
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

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CompetitionDtoBuilder {

  }
}
