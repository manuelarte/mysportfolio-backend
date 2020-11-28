package org.manuel.mysportfolio.model.dtos;

import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.bson.types.ObjectId;

@lombok.AllArgsConstructor
@lombok.Getter
public abstract class BaseDto {

  @Null(groups = {New.class, PartialUpdate.class})
  @NotNull(groups = Update.class)
  protected final ObjectId id;

  @Null(groups = New.class)
  @NotNull(groups = {Update.class, PartialUpdate.class})
  protected final Long version;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  protected final String createdBy;

}
