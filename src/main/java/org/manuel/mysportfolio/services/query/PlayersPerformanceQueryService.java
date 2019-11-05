package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Optional;

public interface PlayersPerformanceQueryService {

    @PostAuthorize("hasRole('ROLE_ADMIN') or !returnObject.present or #playerId == authentication.principal.attributes['sub']")
    Optional<Performance> findByMatchIdAndPlayerId(ObjectId matchId, String playerId);

}
