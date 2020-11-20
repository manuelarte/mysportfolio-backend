package org.manuel.mysportfolio.model.entities.user;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class AppUser extends BaseDocument {

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

  public AppUser(final String fullName, final String email, final String externalId,
      final AppMembership appMembership, final Boolean admin, final String registrationToken,
      final AppSettings settings) {
    this.fullName = fullName;
    this.email = email;
    this.externalId = externalId;
    this.appMembership = appMembership;
    this.admin = admin;
    this.registrationToken = registrationToken;
    this.settings = settings;
  }

}
