package org.manuel.mysportfolio.model.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

@lombok.AllArgsConstructor
@lombok.Getter
public abstract class BaseDto {

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  @NotNull(groups = UpdateEntity.class)
  protected final String id;

  @Null(groups = NewEntity.class)
  @NotNull(groups = {UpdateEntity.class, PartialUpdateEntity.class})
  protected final Long version;

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  protected final String createdBy;

}
