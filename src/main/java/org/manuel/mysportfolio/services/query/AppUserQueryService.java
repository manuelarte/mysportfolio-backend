package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

public interface AppUserQueryService {

    // TODO add rights
    Optional<AppUser> findOne(ObjectId id);

    // TODO add rights
    @PreAuthorize("#externalId == authentication.principal.attributes['sub'] or hasRole('ROLE_ADMIN')")
    Optional<AppUser> findByExternalId(String externalId);

    @Secured("ROLE_ADMIN")
    @RolesAllowed("ROLE_ADMIN")
    Page<AppUser> findAll(Pageable pageable);

}
