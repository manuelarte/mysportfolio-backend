package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.Place;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.*;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> implements Auditable<String, ObjectId, Instant> {

    @Id
    private ObjectId id;

    private ObjectId competitionId;

    @Version
    private Long version;

    @NotNull
    private Sport sport;

    private SportType type;

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

    private String description;

    @Size(max = 5)
    private Set<String> chips;

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
}
