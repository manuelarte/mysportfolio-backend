package org.manuel.mysportfolio.model.dtos.team;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class AnonymousTeamDtoTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(AnonymousTeamDto.class);
  }

}
