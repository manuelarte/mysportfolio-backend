package org.manuel.mysportfolio.model.entities;

import java.time.DayOfWeek;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportDependent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "competitions")
@lombok.Data
@lombok.NoArgsConstructor
public class Competition extends BaseEntity implements SportDependent {

    @NotNull
    private String name;

    @NotNull
    private Sport sport;

    private DayOfWeek defaultMatchDay;

    @Size(max = 200)
    private String description;

    public Competition(final ObjectId id, final Long lockVersion, final String name, final Sport sport, final DayOfWeek defaultMatchDay, final String description) {
        super(id, lockVersion);
        this.name = name;
        this.sport = sport;
        this.defaultMatchDay = defaultMatchDay;
        this.description = description;
    }

    public Competition(final String name, final Sport sport, final DayOfWeek defaultMatchDay, final String description) {
        this(null, null, name, sport, defaultMatchDay, description);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

}
