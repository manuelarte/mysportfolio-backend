package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionQueryService extends BaseDocumentQueryService<Competition> {

  Page<Competition> findAllByQueryAndCreatedBy(Pageable pageable, String createdBy, Query query);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
  List<Competition> findAllPlaying(DayOfWeek dayOfWeek);

}
