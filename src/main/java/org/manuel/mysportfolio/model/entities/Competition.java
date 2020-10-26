package org.manuel.mysportfolio.model.entities;

import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate.FromToType;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.FromDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.ToDate;
import java.time.DayOfWeek;
import java.time.YearMonth;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.constants.Constants;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportDependent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "competitions")
@FromAndToDate(FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO)
@lombok.Data
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Competition extends BaseEntity implements SportDependent {

  @NotNull
  @Size(max = Constants.COMPETITION_NAME_MAX_CHARACTERS)
  private String name;

  @NotNull
  private Sport sport;

  private DayOfWeek defaultMatchDay;

  @FromDate
  private YearMonth from;

  @ToDate
  private YearMonth to;

  @Size(max = 200)
  private String description;

  public Competition(final ObjectId id, final Long lockVersion, final String name,
      final Sport sport, final DayOfWeek defaultMatchDay,
      final YearMonth from, final YearMonth to, final String description) {
    super(id, lockVersion);
    this.name = name;
    this.sport = sport;
    this.defaultMatchDay = defaultMatchDay;
    this.from = from;
    this.to = to;
    this.description = description;
  }

  public Competition(final String name, final Sport sport, final DayOfWeek defaultMatchDay,
      final YearMonth startDate, final YearMonth endDate, final String description) {
    this(null, null, name, sport, defaultMatchDay,
        startDate, endDate, description);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }

}
