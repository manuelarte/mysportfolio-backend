package org.manuel.mysportfolio.config;

import java.util.Optional;

public interface UserIdProvider {

  Optional<String> getUserId();

}
