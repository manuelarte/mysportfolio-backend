package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;

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

    @CreatedBy
    @NotNull
    private String createdBy;

    @Override
    public String getType() {
        return "registered";
    }

}
