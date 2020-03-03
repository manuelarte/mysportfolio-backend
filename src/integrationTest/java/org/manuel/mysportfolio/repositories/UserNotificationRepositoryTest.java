package org.manuel.mysportfolio.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserNotificationRepositoryTest {

  @Autowired
  private UserNotificationRepository userNotificationRepository;

  @DisplayName("save team add user notification")
  @Test
  public void testSaveAddUserNotification() {
    final var notification = userNotificationRepository.save(
        new TeamAddUserNotification(null, null, "from", "to", new ObjectId()));
    assertNotNull(notification.getId());
    assertNotNull(notification.getVersion());
  }

  @DisplayName("load team add user notification")
  @Test
  public void testLoadAddUserNotification() {
    final var expected = userNotificationRepository.save(
        new TeamAddUserNotification(null, null, "from", "to", new ObjectId()));

    final var actual = userNotificationRepository.findById(expected.getId());
    assertEquals(expected.getId(), actual.get().getId());
    assertEquals(expected.getVersion(), actual.get().getVersion());
  }

}
