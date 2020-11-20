package org.manuel.mysportfolio.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@AutoConfigurationPackage
@SpringBootConfiguration
public class TeamRepositoryTest {

  private static final String USER_ID = "1234567";

  @Inject
  private TeamRepository teamRepository;

  @Test
  @DisplayName("test count all team inside the year")
  public void testCountAll() {
    final var referenceYear = Year.now();
    createTeam(Instant.now());
    final var lower = referenceYear.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var upper = referenceYear.plusYears(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var actual = teamRepository
        .countAllByCreatedByAndCreatedDateIsBetween(USER_ID, lower, upper);
    assertEquals(1, actual);
  }

  @Test
  @DisplayName("test count all team not inside the year")
  public void testCountAllTeamNotInsideTheYear() {
    final var referenceYear = Year.now();
    createTeam(referenceYear.minusYears(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC));
    final var lower = referenceYear.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var upper = referenceYear.plusYears(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var actual = teamRepository
        .countAllByCreatedByAndCreatedDateIsBetween(USER_ID, lower, upper);
    assertEquals(0, actual);
  }

  private Team createTeam(final Instant createdDate) {
    final var team = new Team(RandomStringUtils.randomAlphabetic(5), null, null);
    team.setCreatedBy(USER_ID);
    team.setLastModifiedBy(USER_ID);
    team.setCreatedDate(createdDate);
    team.setLastModifiedDate(createdDate);
    return teamRepository.save(team);
  }

}
