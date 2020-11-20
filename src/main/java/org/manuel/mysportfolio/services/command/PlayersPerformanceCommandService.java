package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.match.Performance;
import org.bson.types.ObjectId;

public interface PlayersPerformanceCommandService {

  Performance updatePerformance(ObjectId matchId, String playerId, Performance performance);

}
