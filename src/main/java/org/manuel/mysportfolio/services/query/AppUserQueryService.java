package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

public interface AppUserQueryService {

    // TODO add rights
    @PostAuthorize("hasRole('ROLE_ADMIN') or #returnObject.orElse(null)?.externalId == authentication.principal.attributes['sub']")
    Optional<AppUser> findOne(ObjectId id);

    // TODO add rights
    @PreAuthorize("hasRole('ROLE_ADMIN') or #externalId == authentication.principal.attributes['sub']")
    Optional<AppUser> findByExternalId(String externalId);

    @RolesAllowed("ROLE_ADMIN")
    Page<AppUser> findAll(Pageable pageable);

}
