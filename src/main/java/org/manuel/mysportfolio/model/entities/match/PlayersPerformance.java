package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Document(collection = "players-performances")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PlayersPerformance implements Auditable<String, ObjectId, Instant> {

    @Id
    private ObjectId id;

    @Version
    private Long version;

    @NotNull
    // @ExistingMatch
    private ObjectId matchId;

    @NotEmpty
    @Valid
    private Map<String, Performance> playerPerformance = new HashMap<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String LastModifiedBy;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public Performance saveOrUpdate(final String playerId, final Performance performance) {
        this.playerPerformance.put(playerId, performance);
        return performance;
    }

    public Optional<Performance> getPerformance(final String playerId) {
        return Optional.ofNullable(this.playerPerformance.get(playerId));
    }

    private Map<String, Performance> getPlayerPerformance() {
        return this.playerPerformance;
    }

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public Optional<Instant> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(LastModifiedBy);
    }

    @Override
    public Optional<Instant> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
