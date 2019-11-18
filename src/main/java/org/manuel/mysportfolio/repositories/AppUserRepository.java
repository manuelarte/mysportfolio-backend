package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUser, ObjectId> {

    Optional<AppUser> findByExternalId(String externalId);

    Set<AppUser> findByExternalIdInAndRegistrationTokenNotNull(Iterable<String> externalIds);

}
