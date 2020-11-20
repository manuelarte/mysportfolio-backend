package org.manuel.mysportfolio.model.entities.usernotification;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-notifications")
@lombok.NoArgsConstructor
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
public abstract class UserNotification extends BaseDocument {

  @NotNull
  protected String to;

  protected UserNotificationStatus status;

  protected UserNotification(final ObjectId id, final Long version, final String to,
      final UserNotificationStatus status) {
    super(id, version);
    this.to = to;
    this.status = status;
  }

}
