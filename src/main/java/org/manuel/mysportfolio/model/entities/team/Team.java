package org.manuel.mysportfolio.model.entities.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.TeamInfo;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "teams")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Team implements TeamInfo {

    @Id
    private ObjectId id;

    @CreatedBy
    private String playerId;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @org.hibernate.validator.constraints.URL
    private String imageLink;

    @CreatedBy
    @NotNull
    private String createdBy;

    @SuppressWarnings("unused")
    private void setCreator(final String createdBy) {
        this.createdBy = createdBy;
    }

}
