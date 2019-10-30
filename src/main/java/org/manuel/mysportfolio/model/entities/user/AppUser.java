package org.manuel.mysportfolio.model.entities.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Optional;

@Document(collection = "users")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class AppUser implements Auditable<String, ObjectId, Instant> {

    @Id
    private ObjectId id;

    @Version
    private Long version;

    @NotNull
    private String externalId;

    @NotNull
    private AuthProvider authProvider;

    private Membership membership;

    private Boolean admin;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Instant lastModifiedDate;

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
        return Optional.ofNullable(lastModifiedBy);
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
