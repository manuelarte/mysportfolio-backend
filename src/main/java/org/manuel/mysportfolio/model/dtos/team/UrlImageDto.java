package org.manuel.mysportfolio.model.dtos.team;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Immutable
@lombok.AllArgsConstructor
@lombok.Data
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class UrlImageDto implements TeamImageDto {

  @org.hibernate.validator.constraints.URL
  @Size(max = 200)
  @NotNull
  private final String url;

}
