package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface PlayersPerformanceQueryService {

    @PreAuthorize("hasRole('ROLE_ADMIN') or #playerId == authentication.principal.attributes['sub']")
    Optional<Performance> findByMatchIdAndPlayerId(ObjectId matchId, String playerId);

}
