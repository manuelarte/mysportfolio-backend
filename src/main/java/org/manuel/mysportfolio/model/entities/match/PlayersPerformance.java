package org.manuel.mysportfolio.model.entities.match;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.MatchDependent;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players-performances")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PlayersPerformance implements MatchDependent, Auditable<String, ObjectId, Instant> {

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
    private String lastModifiedBy;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public Performance saveOrUpdate(final String playerId, final Performance performance) {
        this.playerPerformance.put(playerId, performance);
        return performance;
    }

    public Optional<Performance> getPerformance(final String playerId) {
        return Optional.ofNullable(this.playerPerformance.get(playerId));
    }

    @SuppressWarnings("unused")
    private Map<String, Performance> getPlayerPerformance() {
        return this.playerPerformance;
    }

    @Override
    public @Nonnull
    Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public @Nonnull
    Optional<Instant> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    @Override
    public @Nonnull
    Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @Override
    public @Nonnull
    Optional<Instant> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
