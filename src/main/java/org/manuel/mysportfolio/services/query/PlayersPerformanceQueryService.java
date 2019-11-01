package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Performance;

import java.util.Optional;

public interface PlayersPerformanceQueryService {

    Optional<Performance> findByMatchIdAndPlayerId(ObjectId matchId, String playerId);
}
