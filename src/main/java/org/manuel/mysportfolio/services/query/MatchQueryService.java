package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MatchQueryService {

    Optional<Match> findOne(ObjectId id);

    Page<Match> findAll(Pageable pageable);

}
