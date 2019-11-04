package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

public interface MatchQueryService {

    Optional<Match<? extends TeamType, ? extends TeamType>> findOne(ObjectId id);

    Page<Match<TeamType, TeamType>> findAllCreatedBy(Pageable pageable, String createdBy);

    Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(Query query, Pageable pageable, String createdBy);

}
