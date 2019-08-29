package org.manuel.mysportfolio.model.entities.match;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players-performances")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PlayersPerformance {

    @Id
    private ObjectId id;

    private String matchId;

    private Map<String, Performance> playerPerformance = new HashMap<>();

    public Performance saveOrUpdate(final String playerId, final Performance performance) {
        this.playerPerformance.put(playerId, performance);
        return performance;
    }

    public Performance getPerformance(final String playerId) {
        return this.playerPerformance.get(playerId);
    }

    private Map<String, Performance> getPlayerPerformance() {
        return this.playerPerformance;
    }

}
