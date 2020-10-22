package org.manuel.mysportfolio.services.query;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.security.access.prepost.PreAuthorize;

public interface FirebaseQueryService {

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SYSTEM') "
      + "or #firebaseId == authentication.principal.attributes['sub']")
  UserRecord findByFirebaseId(String firebaseId) throws FirebaseAuthException;

}
