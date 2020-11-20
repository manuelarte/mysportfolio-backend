package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface MatchRepositoryCustom {

  Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(Query query, Pageable pageable);

  List<Match> findAllByTypeSportOrTypeCompetitionInAndStartDateIsBetween(
      String externalId, Sport sport, LocalDate from, LocalDate to);

}
