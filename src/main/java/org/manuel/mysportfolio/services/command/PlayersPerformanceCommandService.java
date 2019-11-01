package org.manuel.mysportfolio.services.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Performance;

public interface PlayersPerformanceCommandService {

    Performance updatePerformance(ObjectId matchId, String playerId, Performance performance);

}
