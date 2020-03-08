package org.manuel.mysportfolio.model.entities.team;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PlainKitPart.class, name = "plain"),
    @JsonSubTypes.Type(value = StripeKitPart.class, name = "stripes"),
    @JsonSubTypes.Type(value = SleevesPlainKitPart.class, name = "sleeves-plain")})
public interface KitPart {

}
