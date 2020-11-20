package org.manuel.mysportfolio.services.command.impl;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import io.jsonwebtoken.lang.Assert;
import java.time.LocalDate;
import java.util.Collections;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
class TeamToUsersCommandServiceImpl implements TeamToUsersCommandService {

  private final TeamToUsersRepository teamToUsersRepository;

  @Override
  public TeamToUsers save(final TeamToUsers teamToUsers) {
    Assert.isNull(teamToUsers.getId(),
        "The team to users entity already exists: " + teamToUsers.getId());
    return teamToUsersRepository.save(teamToUsers);
  }

  @Override
  public TeamToUsers update(final TeamToUsers teamToUsers) {
    Assert.notNull(teamToUsers, "Can't update a null entity");
    Assert.notNull(teamToUsers.getId(), "Can't update a new team to users entity");
    return teamToUsersRepository.save(teamToUsers);
  }

  @Override
  public UserInTeam updateUserInTeam(final ObjectId teamId, final String userId,
      final UserInTeam userInTeam) {
    return teamToUsersRepository.updateUserInTeam(teamId, userId, userInTeam);
  }

  @Override
  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  @SuppressWarnings("unused")
  public void handleTeamCreatedEvent(final TeamCreatedEvent teamCreatedEvent) {
    log.info("Handling context started event {}.", teamCreatedEvent);
    final var team = (Team) teamCreatedEvent.getSource();
    final var userInTeam = new UserInTeam(LocalDate.now(), null, UserInTeam.UserInTeamRole.PLAYER);
    final var entity = new TeamToUsers();
    entity.setTeamId(team.getId());
    entity.addUser(team.getCreatedBy().get(), userInTeam);
    entity.setAdmins(Collections.singleton(team.getCreatedBy().get()));
    teamToUsersRepository.save(entity);
  }

}
