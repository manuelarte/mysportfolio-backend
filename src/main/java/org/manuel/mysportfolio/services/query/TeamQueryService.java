package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.time.Year;
import java.util.Optional;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamQueryService {

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Page<Team> findAllForUser(Pageable pageable, String userId);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Optional<Team> findOne(ObjectId id);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Page<Team> findAllByIdsIn(Pageable pageable, Set<ObjectId> ids);

  @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
  int countAllByCreatedByInYear(String createdBy, Year year);

}
