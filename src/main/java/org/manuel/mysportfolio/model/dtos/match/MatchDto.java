package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.constants.Constants;
import org.manuel.mysportfolio.model.dtos.PlaceDto;
import org.manuel.mysportfolio.model.dtos.match.events.MatchEventDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchDto<H extends TeamTypeDto, A extends TeamTypeDto> {

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final String id;

  @Null(groups = New.class)
  @NotNull(groups = {Update.class, PartialUpdate.class})
  private final Long version;

  @NotNull(groups = {New.class, Update.class})
  private final MatchTypeDto type;

  @NotNull
  private final Map<String, TeamOption> playedFor;

  private final H homeTeam;

  private final A awayTeam;

  private final List<MatchEventDto> events;

  private final PlaceDto address;

  @PastOrPresent
  @NotNull
  private final Instant startDate;
  private final Instant endDate;

  @Size(max = Constants.MATCH_DESCRIPTION_MAX_CHARACTERS)
  private final String description;

  @Size(max = 5)
  @lombok.Singular
  private final Set<
      @Size(max = 20)
      @NotEmpty
          String> chips;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final String createdBy;

  public static <H extends TeamTypeDto, A extends TeamTypeDto> MatchDtoBuilder builder() {
    return new MatchDtoBuilder<H, A>();
  }

  @AssertTrue
  @JsonIgnore
  @SuppressWarnings("unused")
  private boolean isOneTeamExist() {
    return Objects.nonNull(homeTeam) || Objects.nonNull(awayTeam);
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class MatchDtoBuilder<H extends TeamTypeDto, A extends TeamTypeDto> {

  }

}
