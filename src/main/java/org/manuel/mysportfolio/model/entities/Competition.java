package org.manuel.mysportfolio.model.entities;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.Instant;

@Document(collection = "competitions")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class Competition {

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
    @NotNull
    private String createdBy;

    @CreatedDate
    @NotNull
    private Instant createdDate;

    @SuppressWarnings("unused")
    private void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @SuppressWarnings("unused")
    private void setCreatedDate(final Instant createdDate) {
        this.createdDate = createdDate;
    }

}
