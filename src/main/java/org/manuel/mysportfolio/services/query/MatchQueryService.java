package org.manuel.mysportfolio.services.query;

import org.manuel.mysportfolio.model.entities.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MatchQueryService {

    Optional<Match> findOne(UUID id);

    Page<Match> findAll(Pageable pageable);

}
