package org.manuel.mysportfolio.model.auth;

import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@lombok.Getter
public class AuthPrincipal extends DefaultOAuth2User {

  private AppMembership appMembership;

  public AuthPrincipal(
      final Collection<? extends GrantedAuthority> authorities,
      final Map<String, Object> attributes,
      final AppMembership appMembership) {
    super(authorities, attributes, "name");
    this.appMembership = appMembership;
  }

  public String getUserId() {
    return (String) this.getAttributes().get("sub");
  }

  public String getEmail() {
    return (String) this.getAttributes().get("email");
  }

  public String getPicture() {
    return (String) this.getAttributes().get("picture");
  }

}
