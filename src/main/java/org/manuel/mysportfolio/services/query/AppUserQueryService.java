package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AppUserQueryService {

    // TODO add rights
    @PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM') or returnObject.orElse(null)?.externalId == authentication.principal.attributes['sub']")
    Optional<AppUser> findOne(ObjectId id);

    // TODO add rights
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM') or #externalId == authentication.principal.attributes['sub']")
    Optional<AppUser> findByExternalId(String externalId);

    @RolesAllowed({"ROLE_ADMIN", "ROLE_SYSTEM"})
    Page<AppUser> findAll(Pageable pageable);

    @RolesAllowed({"ROLE_ADMIN", "ROLE_SYSTEM"})
    Set<AppUser> findByExternalIds(Collection<String> externalIds);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM') or #externalId == authentication.principal.attributes['sub']")
    Page<UserNotification> getUserNotifications(Pageable pageable, String externalId);

}
