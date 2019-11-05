package org.manuel.mysportfolio.services.command.impl;

import java.util.HashMap;

import org.bson.types.ObjectId;
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
    public Performance updatePerformance(final ObjectId matchId, final String playerId, final Performance performance) {
        final PlayersPerformance playersPerformance = playersPerformanceRepository.findByMatchId(matchId)
                .orElseGet(() -> new PlayersPerformance(null, null, matchId, new HashMap<>(),
                        null, null, null, null));
        // TODO - check if this operation can be done already in MongoDB,
        //  so then you don't need to call saveOrUpdate, and it's done in the database
        playersPerformance.saveOrUpdate(playerId, performance);
        final PlayersPerformance saved = this.playersPerformanceRepository.save(playersPerformance);
        return saved.getPerformance(playerId).get();
    }

}
