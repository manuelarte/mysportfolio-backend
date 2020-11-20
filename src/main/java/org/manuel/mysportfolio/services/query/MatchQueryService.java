package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MatchQueryService {

  @PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Optional<Match<? extends TeamType, ? extends TeamType>> findOne(ObjectId id);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Page<Match<TeamType, TeamType>> findAllCreatedBy(Pageable pageable, String createdBy);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Page<Match<TeamType, TeamType>> findAllBy(Query query, Pageable pageable,
      String createdBy);

  @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
  int countAllByCreatedDateBetweenAndCreatedBy(LocalDate from, LocalDate to, String createdBy);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  Collection<Match<?, ?>> findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(String userId,
      LocalDate from, LocalDate to, Sport sport);

}
