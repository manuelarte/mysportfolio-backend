package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfile;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerProfileRepository extends
    PagingAndSortingRepository<PlayerProfile, ObjectId> {

  Optional<PlayerProfile> findByExternalIdIs(String externalId);

}
