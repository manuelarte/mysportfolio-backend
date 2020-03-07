package org.manuel.mysportfolio.model.entities;

import java.time.DayOfWeek;
import java.time.YearMonth;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportDependent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "competitions")
@lombok.Data
@lombok.NoArgsConstructor
public class Competition extends BaseEntity implements SportDependent {

  @NotNull
  private String name;

  @NotNull
  private Sport sport;

  private DayOfWeek defaultMatchDay;

  private YearMonth from;

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

  @Override
  public boolean isNew() {
    return id == null;
  }

}
