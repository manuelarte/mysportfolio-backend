package org.manuel.mysportfolio.model.entities.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.TeamInfo;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Optional;

@Document(collection = "teams")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class Team implements TeamInfo, Auditable<String, ObjectId, Instant> {

    @Id
    private ObjectId id;

    @Version
    private Long version;

    @NotEmpty
    @Size(max = 30)
    private String name;

    private TeamImage teamImage;

    @NotNull
    private TeamKit teamKit;

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
