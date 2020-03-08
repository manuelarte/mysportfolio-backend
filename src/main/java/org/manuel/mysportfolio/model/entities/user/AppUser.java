package org.manuel.mysportfolio.model.entities.user;

import java.time.Instant;
import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class AppUser implements Auditable<String, ObjectId, Instant> {

  @Id
  private ObjectId id;

  @Version
  private Long version;

  @NotNull
  private String fullName;

  @Email
  @NotNull
  private String email;

  @NotNull
  @Indexed(unique = true)
  private String externalId;

  @NotNull
  private AppMembership appMembership;

  private Boolean admin;

  private String registrationToken;

  private AppSettings settings;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private Instant lastModifiedDate;

  @Override
  public Optional<String> getCreatedBy() {
    return Optional.ofNullable(createdBy);
  }

  @Override
  public Optional<Instant> getCreatedDate() {
    return Optional.ofNullable(createdDate);
  }

  @Override
  public Optional<String> getLastModifiedBy() {
    return Optional.ofNullable(lastModifiedBy);
  }

  @Override
  public Optional<Instant> getLastModifiedDate() {
    return Optional.ofNullable(lastModifiedDate);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }

}
