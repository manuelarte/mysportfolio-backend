package org.manuel.mysportfolio.model.dtos.usernotification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TeamAddUserNotificationDto.class, name = "team-add-user")
})
public interface UserNotificationDto {

  String getId();

  Long getVersion();

}
