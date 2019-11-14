package org.manuel.mysportfolio.model.entities.team;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UrlImage.class, name = "url"),
        @JsonSubTypes.Type(value = KitBasicImage.class, name = "kit-basic")})
public interface TeamImage {

}
