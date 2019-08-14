package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.UUID;

@JsonDeserialize(builder = PlayerDto.PlayerDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class PlayerDto {

    @NotNull
    private final String id;

    @NotNull
    @Max(200)
    private final String fullName;

    @org.hibernate.validator.constraints.URL
    private final URL imageLink;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PlayerDtoBuilder {

    }

}
