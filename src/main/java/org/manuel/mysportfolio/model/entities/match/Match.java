package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
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

    @NotNull
    private HomeTeamType homeTeam;

    @NotNull
    private AwayTeamType awayTeam;

    @NotNull
    private Map<String, TeamOption> playedFor;

    private String address;

    @NotNull
    @PastOrPresent
    private Instant startDate;
    private Instant endDate;

    private List<MatchEvent> events = new ArrayList<>();

    private String description;

    private Set<String> chips;

    @CreatedBy
    @NotNull
    private String createdBy;

    @CreatedDate
    @NotNull
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
