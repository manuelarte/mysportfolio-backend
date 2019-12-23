package org.manuel.mysportfolio.model.entities;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportDependent;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Optional;

@Document(collection = "competitions")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class Competition implements SportDependent, Auditable<String, ObjectId, Instant> {

    @Id
    private ObjectId id;

    @Version
    private Long version;

    @NotNull
    private String name;

    @NotNull
    private Sport sport;

    private DayOfWeek defaultMatchDay;

    @Size(max = 200)
    private String description;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Instant lastModifiedDate;

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
