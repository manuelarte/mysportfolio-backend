package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.Optional;

public interface MatchQueryService {

    @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.orElse(null)?.createdBy.get() == authentication.principal.attributes['sub']")
    Optional<Match<? extends TeamType, ? extends TeamType>> findOne(ObjectId id);

    @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
    Page<Match<TeamType, TeamType>> findAllCreatedBy(Pageable pageable, String createdBy);

    @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
    // @PreAuthorize() check if I can query, by checking the query, I can check that the start date is set,
        // not created by, etc
    Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(Query query, Pageable pageable, String createdBy);

    @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
    int countAllByCreatedDateBetweenAndCreatedBy(LocalDate from, LocalDate to, String createdBy);

}
