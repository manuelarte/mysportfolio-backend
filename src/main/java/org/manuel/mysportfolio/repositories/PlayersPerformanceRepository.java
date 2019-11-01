package org.manuel.mysportfolio.repositories;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersPerformanceRepository extends CrudRepository<PlayersPerformance, ObjectId> {

    Optional<PlayersPerformance> findByMatchId(ObjectId matchId);

}
