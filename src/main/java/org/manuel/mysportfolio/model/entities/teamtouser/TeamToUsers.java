package org.manuel.mysportfolio.model.entities.teamtouser;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.BaseEntity;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@Document(collection = "team2User")
@lombok.Data
@lombok.NoArgsConstructor
public class TeamToUsers extends BaseEntity {

    public TeamToUsers(final ObjectId id, final Long version, final ObjectId teamId, final Map<String, UserInTeam> users, final Set<String> admins) {
        super(id, version);
        this.teamId = teamId;
        this.users = users;
        this.admins = admins;
    }

    @NotNull
    private ObjectId teamId;

    @Valid
    @NotEmpty
    private Map<String, UserInTeam> users;

    @NotEmpty
    private Set<String> admins;

}
