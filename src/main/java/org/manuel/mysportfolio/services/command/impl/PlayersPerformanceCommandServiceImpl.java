package org.manuel.mysportfolio.services.command.impl;

import java.util.HashMap;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.manuel.mysportfolio.repositories.PlayersPerformanceRepository;
import org.manuel.mysportfolio.services.command.PlayersPerformanceCommandService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class PlayersPerformanceCommandServiceImpl implements PlayersPerformanceCommandService {

    private final PlayersPerformanceRepository playersPerformanceRepository;

    @Override
    public Performance updatePerformance(final String matchId, final String playerId, final Performance performance) {
        final PlayersPerformance playersPerformance = playersPerformanceRepository.findByMatchId(matchId)
                .orElseGet(() -> new PlayersPerformance(null, matchId, new HashMap<>()));
        playersPerformance.saveOrUpdate(playerId, performance);
        final PlayersPerformance saved = this.playersPerformanceRepository.save(playersPerformance);
        return saved.getPerformance(playerId);
    }

}
