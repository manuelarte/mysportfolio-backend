package org.manuel.mysportfolio.services.query;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamQueryService {

    Page<Team> findAllCreatedBy(Pageable pageable, String createdBy);

    Optional<Team> findOne(ObjectId id);

}
