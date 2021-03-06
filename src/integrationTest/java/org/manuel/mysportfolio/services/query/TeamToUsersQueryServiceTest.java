package org.manuel.mysportfolio.services.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@Import(ItConfiguration.class)
class TeamToUsersQueryServiceTest {

  private static final String USER_ID = "123456789";

  @Inject
  private TeamToUsersQueryService teamToUsersQueryService;

  @Inject
  private TeamToUsersRepository teamToUsersRepository;

  @AfterEach
  public void tearDown() {
    teamToUsersRepository.deleteAll();
  }

  @Test
  public void testFindByUsersExistsSameUserPermissions() {
    final var actual = new TeamToUsers(null, null, new ObjectId(),
        Collections.singletonMap(USER_ID,
            new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now(),
                Collections.singleton(UserInTeam.UserInTeamRole.PLAYER))),
        Collections.singleton(USER_ID));
    teamToUsersRepository.save(actual);

    final List<TeamToUsers> byUsersContaining;
    try {
      SecurityContextHolder.getContext().setAuthentication(TestUtils.createAuthentication(USER_ID));
      byUsersContaining = teamToUsersQueryService.findByUsersExists(USER_ID);
    } finally {
      SecurityContextHolder.clearContext();
    }
    assertEquals(1, byUsersContaining.size());
  }

  @Test
  public void testFindByUsersExistsDifferentUserPermissions() {
    final var actual = new TeamToUsers(null, null, new ObjectId(),
        Collections.singletonMap(USER_ID,
            new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now(),
                Collections.singleton(UserInTeam.UserInTeamRole.PLAYER))),
        Collections.singleton(USER_ID));
    teamToUsersRepository.save(actual);

    try {
      SecurityContextHolder.getContext()
          .setAuthentication(TestUtils.createAuthentication("otherUser"));
      assertThrows(AccessDeniedException.class,
          () -> teamToUsersQueryService.findByUsersExists(USER_ID));
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

}