package org.manuel.mysportfolio;

import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;

public class TestUtils {

  public static AnonymousTeam createMockAnonymousTeam() {
    final var anonymousTeam = new AnonymousTeam();
    anonymousTeam.setName("teamName");
    return anonymousTeam;
  }
}
