package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.*;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> {

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
    @Past
    private Instant startDate;
    private Instant endDate;

    private List<MatchEvent> events = new ArrayList<>();

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

    private String description;

    private Set<String> chips;

    @SuppressWarnings("unused")
    private void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @SuppressWarnings("unused")
    private void setCreatedDate(final Instant createdDate) {
        this.createdDate = createdDate;
    }

    @SuppressWarnings("unused")
    private void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @SuppressWarnings("unused")
    private void setLastModifiedDate(final Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @AssertTrue
    private boolean isOneTeamExist() {
        return Objects.nonNull(homeTeam) || Objects.nonNull(awayTeam);
    }

}
