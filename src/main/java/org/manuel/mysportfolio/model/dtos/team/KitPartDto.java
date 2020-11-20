package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.manuel.mysportfolio.model.dtos.team.kits.PlainKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.SleevesPlainKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.StripeKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.VShapeKitPartDto;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PlainKitPartDto.class, name = "plain"),
    @JsonSubTypes.Type(value = StripeKitPartDto.class, name = "stripes"),
    @JsonSubTypes.Type(value = SleevesPlainKitPartDto.class, name = "sleeves-plain"),
    @JsonSubTypes.Type(value = VShapeKitPartDto.class, name = "v-shape")})
public interface KitPartDto {

}
