package org.manuel.mysportfolio.repositories;

import java.util.Optional;
import java.util.Set;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUser, ObjectId> {

  Optional<AppUser> findByExternalId(String externalId);

  boolean existsByExternalId(String externalId);

  Set<AppUser> findByExternalIdInAndRegistrationTokenNotNull(Iterable<String> externalIds);

}
