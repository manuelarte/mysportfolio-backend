package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository
    extends PagingAndSortingRepository<Match<TeamType, TeamType>, ObjectId>,
    MatchRepositoryCustom, BaseDocumentRepository<Match<TeamType, TeamType>> {

}
