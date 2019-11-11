package org.manuel.mysportfolio.model.entities;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;

import java.time.Instant;
import java.util.Optional;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
public class BaseEntity implements Auditable<String, ObjectId, Instant> {

    @Id
    protected ObjectId id;

    @Version
    protected Long version;

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    protected Instant createdDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    protected Instant lastModifiedDate;

    public BaseEntity(final ObjectId id, final Long version) {
        this.id = id;
        this.version = version;
    }

    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public Optional<Instant> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public Optional<Instant> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
