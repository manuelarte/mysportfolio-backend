package org.manuel.mysportfolio.services.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
class TeamToUsersQueryServiceTest {

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
    final String userId = "123456789";
    final var actual = new TeamToUsers(null, null, new ObjectId(),
        Collections.singletonMap(userId,
            new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now(),
                Collections.singleton(UserInTeam.UserInTeamRole.PLAYER))),
        Collections.singleton("123456789"));
    teamToUsersRepository.save(actual);

    final List<TeamToUsers> byUsersContaining;
    try {
      SecurityContextHolder.getContext().setAuthentication(TestUtils.createAuthentication(userId));
      byUsersContaining = teamToUsersQueryService.findByUsersExists(userId);
    } finally {
      SecurityContextHolder.clearContext();
    }
    assertEquals(1, byUsersContaining.size());
  }

  @Test
  public void testFindByUsersExistsDifferentUserPermissions() {
    final String userId = "123456789";
    final var actual = new TeamToUsers(null, null, new ObjectId(),
        Collections.singletonMap(userId,
            new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now(),
                Collections.singleton(UserInTeam.UserInTeamRole.PLAYER))),
        Collections.singleton("123456789"));
    teamToUsersRepository.save(actual);

    try {
      SecurityContextHolder.getContext()
          .setAuthentication(TestUtils.createAuthentication("otherUser"));
      assertThrows(AccessDeniedException.class,
          () -> teamToUsersQueryService.findByUsersExists(userId));
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

}