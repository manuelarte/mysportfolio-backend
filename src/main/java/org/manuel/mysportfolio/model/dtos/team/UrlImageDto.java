package org.manuel.mysportfolio.model.dtos.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UrlImageDto implements TeamImageDto {

  @org.hibernate.validator.constraints.URL
  @Size(max = 200)
  @NotNull
  private String url;

}
