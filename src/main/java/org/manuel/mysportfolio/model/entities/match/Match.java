package org.manuel.mysportfolio.model.entities.match;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Place;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.manuel.mysportfolio.model.entities.match.type.MatchType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> implements
    Auditable<String, ObjectId, Instant> {

  @Id
  private ObjectId id;

  @NotNull
  private MatchType type;

  @Version
  private Long version;

  private HomeTeamType homeTeam;

  private AwayTeamType awayTeam;

  @NotNull
  private Map<String, TeamOption> playedFor;

  private Place address;

  @NotNull
  @PastOrPresent
  private Instant startDate;
  private Instant endDate;

  private List<MatchEvent> events = new ArrayList<>();

  @Size(max = 300)
  private String description;

  @Size(max = 5)
  private Set<
      @Size(max = 20)
      @NotEmpty
          String> chips = new HashSet<>();

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private Instant lastModifiedDate;

  public Optional<String> getCreatedBy() {
    return Optional.ofNullable(createdBy);
  }

  public Optional<Instant> getCreatedDate() {
    return Optional.ofNullable(createdDate);
  }

  public Optional<String> getLastModifiedBy() {
    return Optional.ofNullable(lastModifiedBy);
  }

  public Optional<Instant> getLastModifiedDate() {
    return Optional.ofNullable(lastModifiedDate);
  }

  @AssertTrue
  private boolean isOneTeamExist() {
    return Objects.nonNull(homeTeam) || Objects.nonNull(awayTeam);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }

  public int getGoals(final TeamOption teamOption) {
    return (int) Optional.ofNullable(events).orElse(Collections.emptyList()).stream()
        .filter(it -> it instanceof GoalMatchEvent)
        .filter(it -> ((GoalMatchEvent) it).getTeam() == teamOption).count();
  }

}
