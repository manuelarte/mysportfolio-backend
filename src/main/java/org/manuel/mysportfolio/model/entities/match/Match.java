package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> {

    @Id
    private ObjectId id;

    @NotNull
    private Sport sport;

    private SportType type;

    @NotNull
    private HomeTeamType homeTeam;

    @NotNull
    private AwayTeamType awayTeam;

    @NotNull
    private TeamOption playedFor;

    private String address;

    @NotNull
    @Past
    private Instant startDate;
    private Instant endDate;

    private List<MatchEvent> events = new ArrayList<>();

    @CreatedBy
    @NotNull
    private String createdBy;

    private String description;

    private Set<String> chips;

    @SuppressWarnings("unused")
    private void setCreator(final String createdBy) {
        this.createdBy = createdBy;
    }

}
