package org.manuel.mysportfolio.repositories;

import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface CompetitionRepositoryCustom {

  Page<Competition> findAllByQuery(Pageable pageable, Query query);

}
