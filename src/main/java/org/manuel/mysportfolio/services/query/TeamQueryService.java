package org.manuel.mysportfolio.services.query;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;

public interface TeamQueryService {

    Page<Team> findAllCreatedBy(Pageable pageable, String createdBy);

    @PostAuthorize("hasRole('ROLE_ADMIN') or #returnObject.orElse(null)?.createdBy == authentication.principal.attributes['sub']")
    Optional<Team> findOne(ObjectId id);

}
