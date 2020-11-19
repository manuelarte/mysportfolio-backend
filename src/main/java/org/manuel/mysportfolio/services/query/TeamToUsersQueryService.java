package org.manuel.mysportfolio.services.query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamToUsersQueryService {

  // only admins or people that one time where in the team can get that info
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Optional<TeamToUsers> findByTeamId(ObjectId teamId);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  List<TeamToUsers> findByTeamIdIn(Collection<ObjectId> teamIds);

  // admin or the same user can perform this call
  @PreAuthorize("hasRole('ROLE_ADMIN') or #externalUserId == authentication.principal.attributes['sub']")
  List<TeamToUsers> findByUsersExists(String externalUserId);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER') or #externalUserId == authentication.principal.attributes['sub']")
  Page<TeamToUsers> findAllByUserExists(Pageable pageable, String externalUserId);


}
