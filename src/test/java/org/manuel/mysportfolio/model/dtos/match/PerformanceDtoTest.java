package org.manuel.mysportfolio.model.dtos.match;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class PerformanceDtoTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(PerformanceDto.class)
        .verify();
  }

  @Test
  public void checkMyClassIsImmutable() {
    assertImmutable(PerformanceDto.class);
  }

}
