package org.manuel.mysportfolio;

import java.util.function.Supplier;
import org.manuel.mysportfolio.config.SystemAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

  public static <T> T doWithSystemAuthentication(final Supplier<T> action) {
    final Authentication previous = SecurityContextHolder.getContext().getAuthentication();
    try {
      SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
      return action.get();
    } finally {
      SecurityContextHolder.getContext().setAuthentication(previous);
    }
  }
}
