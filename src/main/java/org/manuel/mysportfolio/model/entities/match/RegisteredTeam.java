package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class RegisteredTeam implements TeamType {

    @NotNull
    private ObjectId teamId;

    @Override
    public String getType() {
        return "registered";
    }

}
