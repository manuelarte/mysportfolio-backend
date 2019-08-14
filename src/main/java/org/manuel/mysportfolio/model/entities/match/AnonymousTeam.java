package org.manuel.mysportfolio.model.entities.match;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.net.URL;
import java.util.UUID;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class AnonymousTeam implements TeamType, TeamInfo {

    private ObjectId id;

    @NotEmpty
    @Max(30)
    private String name;

    @org.hibernate.validator.constraints.URL
    private URL imageLink;

    @Override
    public String getType() {
        return "anonymous";
    }

}
