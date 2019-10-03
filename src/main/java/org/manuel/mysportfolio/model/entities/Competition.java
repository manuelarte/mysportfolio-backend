package org.manuel.mysportfolio.model.entities;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Document(collection = "competitions")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Competition {

    @Id
    private ObjectId id;

    @Version
    private Long version;

    @NotNull
    private String name;

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
