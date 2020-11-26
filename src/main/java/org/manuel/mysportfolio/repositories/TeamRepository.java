package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.spring.manuelartevalidation.repositories.CrpudRepository;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, ObjectId>,
    CrpudRepository<Team, ObjectId>, BaseDocumentRepository<Team> {

  Page<Team> findAllByIdIsIn(Pageable pageable, Set<ObjectId> ids);

}
