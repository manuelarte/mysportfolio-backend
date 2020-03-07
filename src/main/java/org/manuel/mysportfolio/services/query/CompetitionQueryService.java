package org.manuel.mysportfolio.services.query;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionQueryService {

  //@PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.orElse(null)?.createdBy == authentication.principal.attributes['sub']")
  Page<Competition> findAllCreatedBy(Pageable pageable, String createdBy);

  @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.orElse(null)?.createdBy.get() == authentication.principal.attributes['sub']")
  Optional<Competition> findOne(ObjectId id);

  @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
  int countAllByCreatedByInYear(String createdBy, Year year);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
  List<Competition> findAllPlaying(DayOfWeek dayOfWeek);

}
