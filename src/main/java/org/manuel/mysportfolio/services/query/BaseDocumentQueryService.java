package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.threeten.extra.Interval;

public interface BaseDocumentQueryService<T extends BaseDocument> {

  @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
  Optional<T> findOne(ObjectId id);

  @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
  Page<T> findAllCreatedBy(String createdBy, Pageable pageable);

  @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
  int countAllByCreatedByInInterval(String createdBy, Interval interval);

}
