package org.manuel.mysportfolio.services.query.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.manuel.mysportfolio.services.query.FirebaseQueryService;
import org.springframework.stereotype.Component;

@Component
public class FirebaseQueryServiceImpl implements FirebaseQueryService {

  @Override
  public UserRecord findByFirebaseId(final String firebaseId) throws FirebaseAuthException {
    return FirebaseAuth.getInstance().getUser(firebaseId);
  }

}
