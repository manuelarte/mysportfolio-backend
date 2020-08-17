package org.manuel.mysportfolio.model.dtos.team;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class RegisteredTeamDtoTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(RegisteredTeamDto.class)
        .verify();
  }

  @Test
  public void checkMyClassIsImmutable() {
    assertImmutable(RegisteredTeamDto.class);
  }

}
