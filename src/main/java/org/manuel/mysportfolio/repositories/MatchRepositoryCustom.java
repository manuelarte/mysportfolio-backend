package org.manuel.mysportfolio.repositories;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface MatchRepositoryCustom {

    Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(Query query, Pageable pageable);

}
