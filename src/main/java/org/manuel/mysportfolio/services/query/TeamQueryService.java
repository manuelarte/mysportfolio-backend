package org.manuel.mysportfolio.services.query;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;

public interface TeamQueryService {

    Optional<Team> findOne(ObjectId id);

}
