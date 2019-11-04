package org.manuel.mysportfolio.model.entities.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.TeamInfo;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Document(collection = "teams")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class Team implements TeamInfo {

    @Id
    private ObjectId id;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @org.hibernate.validator.constraints.URL
    @Size(max = 200)
    private String imageLink;

    @CreatedBy
    @NotNull
    private String createdBy;

    @CreatedDate
    @NotNull
    private Instant createdDate;

    @SuppressWarnings("unused")
    private void setCreator(final String createdBy) {
        this.createdBy = createdBy;
    }

}
