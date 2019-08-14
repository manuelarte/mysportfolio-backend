package org.manuel.mysportfolio.model.entities.team;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.TeamInfo;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.net.URL;
import java.util.UUID;

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
    @Max(30)
    private String name;

    @org.hibernate.validator.constraints.URL
    private URL imageLink;

}
