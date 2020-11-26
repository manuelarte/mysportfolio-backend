package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseDocumentRepository<T extends BaseDocument> {

  Page<T> findAllByCreatedBy(String userId, Pageable pageable);

  int countAllByCreatedByAndCreatedDateIsBetween(String createdBy, Instant lowerLimit, Instant upperThan);

}
