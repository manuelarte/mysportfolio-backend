package org.manuel.mysportfolio.model.dtos.team;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class TeamDtoTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(TeamDto.class)
        .verify();
  }

}
