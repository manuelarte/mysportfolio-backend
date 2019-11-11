package org.manuel.mysportfolio.model.dtos.teamtousers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Map;
import java.util.Set;

@JsonDeserialize(builder = TeamToUsersDto.TeamToUsersDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamToUsersDto {

    @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
    @NotNull(groups = UpdateEntity.class)
    private final String id;

    @Null(groups = NewEntity.class)
    @NotNull(groups = { UpdateEntity.class, PartialUpdateEntity.class })
    private final Long version;

    @Null(groups = {PartialUpdateEntity.class, PartialUpdateEntity.class})
    @NotNull(groups = NewEntity.class)
    private String teamId;

    @NotEmpty(groups = { NewEntity.class, UpdateEntity.class})
    private Map<String, UserInTeam> users;

    @NotEmpty(groups = { NewEntity.class, UpdateEntity.class})
    private Set<String> admins;

}
