package org.manuel.mysportfolio.model.entities.player;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = FootballSkills.FootballSkillsBuilder.class)
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class FootballSkills implements SportSkills {

  @Max(100)
  @Min(0)
  private Integer pace;
  @Max(100)
  @Min(0)
  private Integer shooting;
  @Max(100)
  @Min(0)
  private Integer passing;
  @Max(100)
  @Min(0)
  private Integer dribbling;
  @Max(100)
  @Min(0)
  private Integer defence;
  @Max(100)
  @Min(0)
  private Integer physical;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class FootballSkillsBuilder {

  }

}
