package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.documents.playersperformance.Performance;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PostAuthorize;

public interface PlayersPerformanceQueryService {

  @PostAuthorize("hasRole('ROLE_ADMIN') or !returnObject.present "
      + "or #playerId == authentication.principal.attributes['sub']")
  Optional<Performance> findByMatchIdAndPlayerId(ObjectId matchId, String playerId);

}
