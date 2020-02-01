package org.manuel.mysportfolio.repositories;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.player.PlayerProfile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerProfileRepository extends PagingAndSortingRepository<PlayerProfile, ObjectId> {

  Optional<PlayerProfile> findByExternalIdIs(String externalId);

}
