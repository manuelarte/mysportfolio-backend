package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CompetitionQueryService {

    Optional<Competition> findOne(ObjectId id);

    Page<Competition> findAllCreatedBy(Pageable pageable, String createdBy);

}
