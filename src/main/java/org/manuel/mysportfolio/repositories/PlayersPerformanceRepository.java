package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.match.PlayersPerformance;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersPerformanceRepository extends CrudRepository<PlayersPerformance, ObjectId> {

  Optional<PlayersPerformance> findByMatchId(ObjectId matchId);

}
