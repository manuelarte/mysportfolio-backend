package org.manuel.mysportfolio.services.query.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.manuel.mysportfolio.repositories.PlayersPerformanceRepository;
import org.manuel.mysportfolio.services.query.PlayersPerformanceQueryService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@lombok.AllArgsConstructor
public class PlayersPerformanceQueryServiceImpl implements PlayersPerformanceQueryService {

    private final PlayersPerformanceRepository playersPerformanceRepository;

    @Override
    public Optional<Performance> findByMatchIdAndPlayerId(final ObjectId matchId, final String playerId) {
        return playersPerformanceRepository.findByMatchId(matchId)
                .flatMap(p -> p.getPerformance(playerId));
    }
}
