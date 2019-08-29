package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.match.Performance;

public interface PlayersPerformanceCommandService {

    Performance updatePerformance(String matchId, String playerId, Performance performance);

}
