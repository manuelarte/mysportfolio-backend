package org.manuel.mysportfolio.repositories.impl;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.util.List;
import org.manuel.mysportfolio.repositories.CompetitionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
class CompetitionRepositoryCustomImpl implements CompetitionRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public Page<Competition> findAllByQuery(final Pageable pageable, final Query query) {
    query.with(pageable);
    final List<Competition> list = mongoTemplate.find(query, Competition.class);
    return PageableExecutionUtils.getPage(list, pageable,
        () -> mongoTemplate.count(query, Competition.class));
  }

}
