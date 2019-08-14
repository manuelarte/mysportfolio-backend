package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> {

    @Id
    private ObjectId id;

    private Sport sport;

    private SportType type;

    private HomeTeamType homeTeam;

    private AwayTeamType awayTeam;

    private Set<String> events = new HashSet<>();

    private String creator;

}
